package ai.pribela.glossapp.dto.suggestion;

import lombok.Getter;

@Getter
public enum WordClass {

    NOUNS("nouns"),
    VERBS("verbs"),
    ADVERBS("adverbs"),
    ADJECTIVES("adjectives");

    private final String value;

    WordClass(String value) {
        this.value = value;
    }

}
