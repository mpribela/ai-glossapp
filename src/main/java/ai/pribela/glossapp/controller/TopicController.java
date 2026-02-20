package ai.pribela.glossapp.controller;

import ai.pribela.glossapp.dto.TopicDto;
import ai.pribela.glossapp.dto.TopicListDto;
import ai.pribela.glossapp.repository.data.Topic;
import ai.pribela.glossapp.service.TopicService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/topic")
public class TopicController {

    private final TopicService topicService;

    public TopicController(TopicService topicService) {
        this.topicService = topicService;
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public TopicListDto getTopics() {
        List<Topic> topics = topicService.getAllTopics();
        return new TopicListDto(topics.stream()
                .map(TopicDto::new)
                .toList());
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TopicDto getTopic(@PathVariable Long id) {
        Topic topic = topicService.getTopic(id);
        return new TopicDto(topic);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public TopicDto createTopic(@Valid @RequestBody TopicDto createTopicDto) {
        Topic topic = topicService.createTopic(createTopicDto);
        return new TopicDto(topic);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TopicDto editTopic(@PathVariable Long id, @RequestBody TopicDto editTopicDto) {
        Topic editedTopic = topicService.editTopic(id, editTopicDto.topic());
        return new TopicDto(editedTopic);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TopicDto deleteTopic(@PathVariable Long id) {
        Topic deletedTopic = topicService.deleteTopic(id);
        return new TopicDto(deletedTopic);
    }

}
