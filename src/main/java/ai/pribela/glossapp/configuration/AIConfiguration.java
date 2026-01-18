package ai.pribela.glossapp.configuration;

import com.google.genai.Client;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.Assert;

@Configuration
public class AIConfiguration {

    @Bean
    public Client googleGenAiClient() {
        String apiKey = System.getenv("GEMINI_API_KEY");
        Assert.hasText(apiKey, "'GEMINI_API_KEY' environment variable is not set");
        return Client.builder()
                .apiKey(apiKey)
                .build();
    }
}
