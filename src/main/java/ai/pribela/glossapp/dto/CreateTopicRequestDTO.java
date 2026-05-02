package ai.pribela.glossapp.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record CreateTopicRequestDTO(
        @NotBlank String topic,
        String description,
        boolean getAISuggestions,
        @NotBlank String sourceLanguage,
        @NotBlank String targetLanguage,
        @NotBlank String proficiencyLevel
) {
}
