package ai.pribela.glossapp.dto.agentic;

import ai.pribela.glossapp.repository.data.Topic;
import ai.pribela.glossapp.repository.data.Word;

import java.util.List;
import java.util.Map;

public record NounAgenticDTO(
        String nounArticle,
        String indefiniteSingularFormNoun,
        String definitiveSingularFormNoun,
        String indefinitePluralFormNoun,
        String definitivePluralFormNoun,
        boolean isCountable,
        String translation,
        List<SentenceAgenticDTO> examples) {

    public Word toEntity(Topic topic) {
        Map<String, Object> attributes = Map.of(
                "nounArticle", nounArticle,
                "isCountable", isCountable,
                "indefiniteSingularFormNoun", indefiniteSingularFormNoun,
                "definitiveSingularFormNoun", definitiveSingularFormNoun,
                "indefinitePluralFormNoun", indefinitePluralFormNoun,
                "definitivePluralFormNoun", definitivePluralFormNoun
        );
        var sentences = examples.stream().map(SentenceAgenticDTO::toEntity).toList();
        return new Word(indefiniteSingularFormNoun, translation, "noun", attributes, topic, sentences);
    }
}
