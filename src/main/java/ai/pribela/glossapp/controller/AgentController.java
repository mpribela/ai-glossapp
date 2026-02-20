package ai.pribela.glossapp.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/agent")
public class AgentController {

    private final ChatClient client;

    public AgentController(ChatClient client) {
        this.client = client;
    }

    @GetMapping(value = "/words/suggestions")
    public String getWordsSuggestions(
            @RequestParam(value = "topic") String topic,
            @RequestParam(value = "language") String language) {
        return client
                .prompt()
                .system(sp -> sp.param("language", language))
                .user(topic)
                .call()
                .content();
    }
}
