package ai.pribela.glossapp.dto;

import java.util.List;

public record NounDTO(
        Long id,
        String nounArticle,
        String indefiniteSingularFormNoun,
        String definitiveSingularFormNoun,
        String indefinitePluralFormNoun,
        String definitivePluralFormNoun,
        boolean isCountable,
        String translation,
        List<SentenceDTO> examples,
        String notes) {
}
