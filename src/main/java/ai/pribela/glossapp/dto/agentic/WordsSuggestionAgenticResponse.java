package ai.pribela.glossapp.dto.agentic;

import ai.pribela.glossapp.dto.SuggestedWord;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public record WordsSuggestionAgenticResponse(
        List<Noun> nouns,
        List<Verb> verbs,
        List<Adjective> adjectives,
        List<Adverb> adverbs) {

    public Map<String, List<SuggestedWord>> asMap() {
        Map<String, List<SuggestedWord>> result = new HashMap<>();
        result.put("nouns", nouns.stream().map(Noun::toSuggestedWord).toList());
        result.put("verbs", verbs.stream().map(Verb::toSuggestedWord).toList());
        result.put("adjectives", adjectives.stream().map(Adjective::toSuggestedWord).toList());
        result.put("adverbs", adverbs.stream().map(Adverb::toSuggestedWord).toList());
        return result;
    }
}
