package ai.pribela.glossapp.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record CreateTopicResponseDTO(
        @NotNull Long id
) {
}
