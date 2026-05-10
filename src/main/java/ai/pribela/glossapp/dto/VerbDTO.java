package ai.pribela.glossapp.dto;

import java.util.List;

public record VerbDTO(
        Long id,
        String verb,
        String translation,
        Boolean isRegular,
        List<VerbFormDTO> forms,
        List<SentenceDTO> examples,
        String notes
) {

}
