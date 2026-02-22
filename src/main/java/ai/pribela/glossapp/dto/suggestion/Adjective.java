package ai.pribela.glossapp.dto.suggestion;

import java.util.List;

public record Adjective(String adjective, String translation, List<Sentence> examples) implements Word {
    @Override
    public WordClass getWordClass() {
        return WordClass.ADJECTIVES;
    }
}
