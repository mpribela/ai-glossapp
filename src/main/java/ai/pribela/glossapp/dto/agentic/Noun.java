package ai.pribela.glossapp.dto.agentic;

import ai.pribela.glossapp.dto.SuggestedSentence;
import ai.pribela.glossapp.dto.SuggestedWord;

import java.util.List;
import java.util.Map;

public record Noun(
        String nounArticle,
        String indefiniteSingularFormNoun,
        String definitiveSingularFormNoun,
        String indefinitePluralFormNoun,
        String definitivePluralFormNoun,
        boolean isCountable,
        String translation,
        List<Sentence> examples) {

    public SuggestedWord toSuggestedWord() {
        return new SuggestedWord(indefinitePluralFormNoun, translation, getSuggestedSentences(), toProperties());
    }

    private List<SuggestedSentence> getSuggestedSentences() {
        return examples.stream()
                .map(example -> new SuggestedSentence(example.original(), example.translation()))
                .toList();
    }

    private Map<String, String> toProperties() {
        return Map.of(
                "article", nounArticle,
                "definitiveSingularFormNoun", definitiveSingularFormNoun,
                "indefinitePluralFormNoun", indefinitePluralFormNoun,
                "definitivePluralFormNoun", definitivePluralFormNoun);
    }
}
