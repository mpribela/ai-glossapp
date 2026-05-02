package ai.pribela.glossapp.dto.agentic;

import ai.pribela.glossapp.dto.SuggestedSentence;
import ai.pribela.glossapp.dto.SuggestedWord;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public record Verb(
        String verb,
        String translation,
        Boolean isRegular,
        List<VerbForm> forms,
        List<Sentence> examples) {


    public SuggestedWord toSuggestedWord() {
        return new SuggestedWord(verb, translation, getSuggestedSentences(), toProperties());
    }

    private List<SuggestedSentence> getSuggestedSentences() {
        return examples.stream()
                .map(example -> new SuggestedSentence(example.original(), example.translation()))
                .toList();
    }

    private Map<String, String> toProperties() {
        var properties = forms.stream().collect(Collectors.toMap(VerbForm::formType, VerbForm::form));
        properties.put("isRegular", isRegular.toString());
        return properties;
    }
}
