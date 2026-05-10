package ai.pribela.glossapp.dto.agentic;

import java.util.List;

public record WordsSuggestionAgenticResponseDTO(
        List<NounAgenticDTO> nouns,
        List<VerbAgenticDTO> verbs,
        List<AdjectiveAgenticDTO> adjectives,
        List<AdverbAgenticDTO> adverbs) {
}
