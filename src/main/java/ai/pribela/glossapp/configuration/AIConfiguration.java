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
            You are an expert AI Language Tutor. Your goal is to help students learn {target_language} by building their vocabulary based on a specific {topic} and {description}.
            Your Task:
            - Provide 10 {word_class} relevant to the topic, suited for a {proficiency_level} learner.
            - For each word, provide a natural example sentence in {target_language}.
            - Provide a clear translation of both the words and the sentences into {source_language}.
            - Noun translations should be in singular indefinite form without an article.
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
