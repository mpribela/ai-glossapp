package ai.pribela.glossapp.controller;

import ai.pribela.glossapp.dto.*;
import ai.pribela.glossapp.repository.data.Learner;
import ai.pribela.glossapp.repository.data.Topic;
import ai.pribela.glossapp.service.TopicService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    public TopicListDto getTopics(@AuthenticationPrincipal Learner learner) {
        List<Topic> topics = topicService.getAllTopics(learner.getId());
        return new TopicListDto(topics.stream()
                .map(TopicDto::new)
                .toList());
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public GetTopicDetailResponseDTO getTopic(@PathVariable Long id, @AuthenticationPrincipal Learner learner) {
        return topicService.getTopic(id, learner);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CreateTopicResponseDTO createTopic(@Valid @RequestBody CreateTopicRequestDTO createTopicRequest, @AuthenticationPrincipal Learner learner) {
        return topicService.createTopic(createTopicRequest, learner);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TopicDto editTopic(@PathVariable Long id, @RequestBody TopicDto editTopicDto, @AuthenticationPrincipal Learner learner) {
        Topic editedTopic = topicService.editTopic(id, editTopicDto.topic(), learner);
        return new TopicDto(editedTopic);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TopicDto deleteTopic(@PathVariable Long id, @AuthenticationPrincipal Learner learner) {
        Topic deletedTopic = topicService.deleteTopic(id, learner);
        return new TopicDto(deletedTopic);
    }

}
