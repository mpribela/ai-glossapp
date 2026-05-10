package ai.pribela.glossapp.dto;

import java.util.List;

public record AdverbDTO(
        Long id,
        String adverb,
        String translation,
        List<SentenceDTO> examples,
        String notes) {
}
