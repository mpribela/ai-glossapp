package ai.pribela.glossapp.dto;

import ai.pribela.glossapp.dto.agentic.WordsSuggestionAgenticResponse;
import ai.pribela.glossapp.repository.data.Topic;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public record CreateTopicResponseDTO(
        Long id,
        String topic,
        String description,
        String sourceLanguage,
        String targetLanguage,
        String proficiencyLevel,
        Map<String, List<SuggestedWord>> suggestedWords) {

    public CreateTopicResponseDTO(Topic topic, WordsSuggestionAgenticResponse agenticResponse) {
        this(topic.getId(), topic.getName(), topic.getDescription(), topic.getSourceLanguage(), topic.getTargetLanguage(), topic.getProficiencyLevel(), agenticResponse.asMap());
    }

    public CreateTopicResponseDTO(Topic topic) {
        this(topic.getId(), topic.getName(), topic.getDescription(), topic.getSourceLanguage(), topic.getTargetLanguage(), topic.getProficiencyLevel(), new HashMap<>());
    }
}
