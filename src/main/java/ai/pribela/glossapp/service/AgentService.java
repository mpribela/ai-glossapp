package ai.pribela.glossapp.service;

import ai.pribela.glossapp.dto.CreateTopicRequestDTO;
import ai.pribela.glossapp.dto.agentic.*;
import ai.pribela.glossapp.service.exception.AgenticException;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.StructuredTaskScope;

import static java.util.concurrent.StructuredTaskScope.Subtask.State.FAILED;
import static java.util.concurrent.StructuredTaskScope.Subtask.State.SUCCESS;

@Component
public class AgentService {

    private final ChatClient client;

    public AgentService(ChatClient client) {
        this.client = client;
    }

    public WordsSuggestionAgenticResponseDTO getTopicWordsSuggestion(CreateTopicRequestDTO request) throws AgenticException {
        try (var scope = StructuredTaskScope.open(StructuredTaskScope.Joiner.awaitAll())) {
            var nouns = scope.fork(() -> fetchNouns(request));
            var verbs = scope.fork(() -> fetchVerbs(request));
            var adjectives = scope.fork(() -> fetchAdjectives(request));
            var adverbs = scope.fork(() -> fetchAdverbs(request));
            scope.join();
            List<NounAgenticDTO> suggestedNouns = nouns.state() != SUCCESS ? null : nouns.get();
            List<VerbAgenticDTO> suggestedVerbs = verbs.state() != SUCCESS ? null : verbs.get();
            List<AdjectiveAgenticDTO> suggestedAdjectives = adjectives.state() != SUCCESS ? null : adjectives.get();
            List<AdverbAgenticDTO> suggestedAdverbs = adverbs.state() != SUCCESS ? null : adverbs.get();
            if (nouns.state() == FAILED && verbs.state() == FAILED && adjectives.state() == FAILED && adverbs.state() == FAILED) {
                throw new AgenticException(List.of(nouns.exception(), verbs.exception(), adjectives.exception(), adverbs.exception()));
            }
            return new WordsSuggestionAgenticResponseDTO(suggestedNouns, suggestedVerbs, suggestedAdjectives, suggestedAdverbs);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
        }
    }

    private List<AdjectiveAgenticDTO> fetchAdjectives(CreateTopicRequestDTO request) {
        return fetchWords(request, "adjectives", new ParameterizedTypeReference<>() {
        });
    }

    private List<NounAgenticDTO> fetchNouns(CreateTopicRequestDTO request) {
        return fetchWords(request, "nouns", new ParameterizedTypeReference<>() {
        });
    }

    private List<VerbAgenticDTO> fetchVerbs(CreateTopicRequestDTO request) {
        return fetchWords(request, "verbs", new ParameterizedTypeReference<>() {
        });
    }

    private List<AdverbAgenticDTO> fetchAdverbs(CreateTopicRequestDTO request) {
        return fetchWords(request, "adverbs", new ParameterizedTypeReference<>() {
        });
    }

    private <T> List<T> fetchWords(CreateTopicRequestDTO request, String wordClass, ParameterizedTypeReference<List<T>> typeReference) {
        return client
                .prompt()
                .advisors(new SimpleLoggerAdvisor())
                .system(sp -> sp.params(Map.of(
                        "topic", request.topic(),
                        "description", request.description(),
                        "proficiency_level", request.proficiencyLevel(),
                        "word_class", wordClass,
                        "target_language", request.targetLanguage(),
                        "source_language", request.sourceLanguage())))
                .call()
                .entity(typeReference);
    }


}
