package ai.pribela.glossapp.dto;

import java.util.List;

public record AdjectiveDTO(
        Long id,
        String adjective,
        String translation,
        List<SentenceDTO> examples,
        String notes) {
}
