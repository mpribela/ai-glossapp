package ai.pribela.glossapp.dto.agentic;

import ai.pribela.glossapp.repository.data.Topic;
import ai.pribela.glossapp.repository.data.Word;

import java.util.List;
import java.util.Map;

public record AdverbAgenticDTO(
        String adverb,
        String translation,
        List<SentenceAgenticDTO> examples) {

    public Word toEntity(Topic topic) {
        var sentences = examples.stream().map(SentenceAgenticDTO::toEntity).toList();
        return new Word(adverb, translation, "adverb", Map.of(), topic, sentences);
    }
}
