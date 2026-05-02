package ai.pribela.glossapp.dto.agentic;

import ai.pribela.glossapp.dto.SuggestedSentence;
import ai.pribela.glossapp.dto.SuggestedWord;

import java.util.HashMap;
import java.util.List;

public record Adverb(
        String adverb,
        String translation,
        List<Sentence> examples) {

    public SuggestedWord toSuggestedWord() {
        return new SuggestedWord(adverb, translation, getSuggestedSentences(), new HashMap<>());
    }

    private List<SuggestedSentence> getSuggestedSentences() {
        return examples.stream()
                .map(example -> new SuggestedSentence(example.original(), example.translation()))
                .toList();
    }
}
