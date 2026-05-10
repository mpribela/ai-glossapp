package ai.pribela.glossapp.dto.transformer;

import ai.pribela.glossapp.dto.*;
import ai.pribela.glossapp.repository.data.Topic;
import ai.pribela.glossapp.repository.data.Word;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class TopicDetailTransformer {

    public GetTopicDetailResponseDTO getTopicDetailResponseDTO(Topic topicEntity) {
        Map<String, List<Word>> wordsByType = groupWordsByType(topicEntity.getWords());
        return new GetTopicDetailResponseDTO(
                topicEntity.getName(),
                topicEntity.getDescription(),
                topicEntity.getSourceLanguage(),
                topicEntity.getTargetLanguage(),
                topicEntity.getProficiencyLevel(),
                mapToVerbDTOs(wordsByType.getOrDefault("verb", List.of())),
                mapToNounDTOs(wordsByType.getOrDefault("noun", List.of())),
                mapToAdjectiveDTOs(wordsByType.getOrDefault("adjective", List.of())),
                mapToAdverbDTOs(wordsByType.getOrDefault("adverb", List.of()))
        );
    }

    private Map<String, List<Word>> groupWordsByType(List<Word> words) {
        return words.stream()
                .collect(Collectors.groupingBy(Word::getType));
    }

    private List<VerbDTO> mapToVerbDTOs(List<Word> words) {
        return words.stream()
                .map(word -> new VerbDTO(
                        word.getId(),
                        word.getText(),
                        word.getTranslation(),
                        getBoolean(word.getAttributes(), "isRegular"),
                        getVerbForms(word.getAttributes()),
                        getSentences(word),
                        word.getNotes()))
                .toList();
    }

    private List<NounDTO> mapToNounDTOs(List<Word> words) {
        return words.stream()
                .map(word -> new NounDTO(
                        word.getId(),
                        word.getAttributes().getOrDefault("nounArticle", "").toString(),
                        word.getAttributes().getOrDefault("indefiniteSingularFormNoun", "").toString(),
                        word.getAttributes().getOrDefault("definitiveSingularFormNoun", "").toString(),
                        word.getAttributes().getOrDefault("indefinitePluralFormNoun", "").toString(),
                        word.getAttributes().getOrDefault("definitivePluralFormNoun", "").toString(),
                        getBoolean(word.getAttributes(), "isCountable"),
                        word.getTranslation(),
                        getSentences(word),
                        word.getNotes()))
                .toList();
    }

    private List<AdjectiveDTO> mapToAdjectiveDTOs(List<Word> words) {
        return words.stream()
                .map(word -> new AdjectiveDTO(
                        word.getId(),
                        word.getText(),
                        word.getTranslation(),
                        getSentences(word),
                        word.getNotes()))
                .toList();
    }

    private List<AdverbDTO> mapToAdverbDTOs(List<Word> words) {
        return words.stream()
                .map(word -> new AdverbDTO(
                        word.getId(),
                        word.getText(),
                        word.getTranslation(),
                        getSentences(word),
                        word.getNotes()))
                .toList();
    }

    private List<SentenceDTO> getSentences(Word word) {
        return word.getSentences().stream().map(SentenceDTO::new).toList();
    }

    private Boolean getBoolean(Map<String, Object> attributes, String key) {
        Object isRegular = attributes.getOrDefault(key, null);
        if (isRegular instanceof Boolean) {
            return (Boolean) isRegular;
        }
        return isRegular != null && Boolean.parseBoolean(isRegular.toString());
    }

    private List<VerbFormDTO> getVerbForms(Map<String, Object> attributes) {
        Object forms = attributes.getOrDefault("forms", Map.of());
        if (forms instanceof Map<?, ?> formsMap) {
            return formsMap.entrySet().stream()
                    .map(entry -> new VerbFormDTO(
                            entry.getKey().toString(),
                            entry.getValue().toString()))
                    .toList();
        }
        return List.of();
    }

}
