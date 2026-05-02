package ai.pribela.glossapp.dto.agentic;

import ai.pribela.glossapp.dto.SuggestedSentence;
import ai.pribela.glossapp.dto.SuggestedWord;

import java.util.HashMap;
import java.util.List;

public record Adjective(
        String adjective,
        String translation,
        List<Sentence> examples) {

    public SuggestedWord toSuggestedWord() {
        return new SuggestedWord(adjective, translation, getSuggestedSentences(), new HashMap<>());
    }

    private List<SuggestedSentence> getSuggestedSentences() {
        return examples.stream()
                .map(example -> new SuggestedSentence(example.original(), example.translation()))
                .toList();
    }

}
