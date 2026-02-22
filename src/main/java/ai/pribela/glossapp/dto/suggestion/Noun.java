package ai.pribela.glossapp.dto.suggestion;

import java.util.List;

public record Noun(
        String nounArticle,
        String indefiniteSingularFormNoun,
        String definitiveSingularFormNoun,
        String indefinitePluralFormNoun,
        String definitivePluralFormNoun,
        boolean isCountable,
        String translation,
        List<Sentence> examples) implements Word {
    @Override
    public WordClass getWordClass() {
        return WordClass.NOUNS;
    }
}
