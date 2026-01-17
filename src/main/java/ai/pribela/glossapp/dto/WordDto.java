package ai.pribela.glossapp.dto;

import ai.pribela.glossapp.repository.data.Word;
import lombok.Builder;

@Builder
public record WordDto(String text, String topic) {
    public WordDto(Word word) {
        this(word.getText(), word.getTopic().getName());
    }
}
