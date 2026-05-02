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

    public WordsSuggestionAgenticResponse getTopicWordsSuggestion(CreateTopicRequestDTO request) throws AgenticException {
        try (var scope = StructuredTaskScope.open(StructuredTaskScope.Joiner.awaitAll())) {
            var nouns = scope.fork(() -> fetchNouns(request));
            var verbs = scope.fork(() -> fetchVerbs(request));
            var adjectives = scope.fork(() -> fetchAdjectives(request));
            var adverbs = scope.fork(() -> fetchAdverbs(request));
            scope.join();
            List<Noun> suggestedNouns = nouns.state() != SUCCESS ? null : nouns.get();
            List<Verb> suggestedVerbs = verbs.state() != SUCCESS ? null : verbs.get();
            List<Adjective> suggestedAdjectives = adjectives.state() != SUCCESS ? null : adjectives.get();
            List<Adverb> suggestedAdverbs = adverbs.state() != SUCCESS ? null : adverbs.get();
            if (nouns.state() == FAILED && verbs.state() == FAILED && adjectives.state() == FAILED && adverbs.state() == FAILED) {
                throw new AgenticException(List.of(nouns.exception(), verbs.exception(), adjectives.exception(), adverbs.exception()));
            }
            return new WordsSuggestionAgenticResponse(suggestedNouns, suggestedVerbs, suggestedAdjectives, suggestedAdverbs);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
        }
    }

    private List<Adjective> fetchAdjectives(CreateTopicRequestDTO request) {
        return fetchWords(request, "adjectives", new ParameterizedTypeReference<>() {
        });
    }

    private List<Noun> fetchNouns(CreateTopicRequestDTO request) {
        return fetchWords(request, "nouns", new ParameterizedTypeReference<>() {
        });
    }

    private List<Verb> fetchVerbs(CreateTopicRequestDTO request) {
        return fetchWords(request, "verbs", new ParameterizedTypeReference<>() {
        });
    }

    private List<Adverb> fetchAdverbs(CreateTopicRequestDTO request) {
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
