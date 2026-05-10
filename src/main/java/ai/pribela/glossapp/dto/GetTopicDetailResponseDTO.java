package ai.pribela.glossapp.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

import java.util.List;

@Builder
public record GetTopicDetailResponseDTO(
        @NotBlank String topic,
        String description,
        @NotBlank String sourceLanguage,
        @NotBlank String targetLanguage,
        @NotBlank String proficiencyLevel,
        List<VerbDTO> verbs,
        List<NounDTO> nouns,
        List<AdjectiveDTO> adjectives,
        List<AdverbDTO> adverbs
) {
}
