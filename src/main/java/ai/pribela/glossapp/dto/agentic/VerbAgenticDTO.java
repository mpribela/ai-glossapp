package ai.pribela.glossapp.dto.agentic;

import ai.pribela.glossapp.repository.data.Topic;
import ai.pribela.glossapp.repository.data.Word;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public record VerbAgenticDTO(
        String verb,
        String translation,
        Boolean isRegular,
        List<VerbFormAgenticDTO> forms,
        List<SentenceAgenticDTO> examples) {

    public Word toEntity(Topic topic) {
        Map<String, String> transformedForms = this.forms.stream().collect(Collectors.toMap(VerbFormAgenticDTO::formType, VerbFormAgenticDTO::form));
        Map<String, Object> attributes = Map.of(
                "isRegular", isRegular,
                "forms", transformedForms
        );
        var sentences = examples.stream().map(SentenceAgenticDTO::toEntity).toList();
        return new Word(verb, translation, "verb", attributes, topic, sentences);
    }
}
