package ai.pribela.glossapp.dto.agentic;

public record SentenceAgenticDTO(
        String original,
        String translation) {

    public ai.pribela.glossapp.repository.data.Sentence toEntity() {
        return new ai.pribela.glossapp.repository.data.Sentence(original, translation);
    }
}
