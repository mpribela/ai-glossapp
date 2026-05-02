package ai.pribela.glossapp.dto;

import java.util.List;
import java.util.Map;

public record SuggestedWord(
        String word,
        String translation,
        List<SuggestedSentence> examples,
        Map<String, String> properties) {
}
