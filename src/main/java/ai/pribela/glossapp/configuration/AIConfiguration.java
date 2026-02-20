package ai.pribela.glossapp.configuration;

import com.google.genai.Client;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.Assert;

@Configuration
public class AIConfiguration {

    // todo parametrise
    private final static String SYSTEM_PROMPT = """
    You are an AI powered assistant to help students with learning a {language} language.
    For given topic you will suggest 50 related words and for each of the word create 2 example sentences.
    For each noun include article. For each verb include all forms and state if the verb is regular or irregular.
    Do not answer anything else.
    """;

    @Bean
    public Client googleGenAiClient() {
        String apiKey = System.getenv("GEMINI_API_KEY");
        Assert.hasText(apiKey, "'GEMINI_API_KEY' environment variable is not set");
        return Client.builder()
                .apiKey(apiKey)
                .build();
    }

    @Bean
    public ChatClient chatClient(ChatClient.Builder clientBuilder) {
        return clientBuilder
                .defaultSystem(SYSTEM_PROMPT)
                .build();
    }
}
