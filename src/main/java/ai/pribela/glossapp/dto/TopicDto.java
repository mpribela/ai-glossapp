package ai.pribela.glossapp.dto;

import ai.pribela.glossapp.repository.data.Topic;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record TopicDto(Long id, @NotBlank String topic) {
    public TopicDto(Topic topic) {
        this(topic.getId(), topic.getName());
    }
}
