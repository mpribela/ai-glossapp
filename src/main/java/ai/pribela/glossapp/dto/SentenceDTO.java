package ai.pribela.glossapp.dto;

import ai.pribela.glossapp.repository.data.Sentence;

public record SentenceDTO(String sentence, String translation) {
    public SentenceDTO(Sentence sentence) {
        this(sentence.getSentence(), sentence.getTranslation());
    }
}
