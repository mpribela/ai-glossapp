package ai.pribela.glossapp.dto.suggestion;

import java.util.List;

public record Verb(String verb, String translation, Boolean isRegular, List<VerbForm> forms, List<Sentence> examples) implements Word {
    @Override
    public WordClass getWordClass() {
        return WordClass.VERBS;
    }
}
