package ai.pribela.glossapp.dto.suggestion;

import java.util.List;

public record WordsSuggestionDto(List<Noun> nouns, List<Verb> verbs, List<Adjective> adjectives) {
}
