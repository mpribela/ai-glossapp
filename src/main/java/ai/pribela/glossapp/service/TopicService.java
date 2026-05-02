package ai.pribela.glossapp.service;

import ai.pribela.glossapp.dto.CreateTopicRequestDTO;
import ai.pribela.glossapp.dto.CreateTopicResponseDTO;
import ai.pribela.glossapp.dto.agentic.WordsSuggestionAgenticResponse;
import ai.pribela.glossapp.exception.TopicNotFoundException;
import ai.pribela.glossapp.repository.TopicRepository;
import ai.pribela.glossapp.repository.data.Learner;
import ai.pribela.glossapp.repository.data.Topic;
import ai.pribela.glossapp.service.exception.AgenticException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class TopicService {

    private final TopicRepository topicRepository;
    private final AgentService agentService;

    public TopicService(TopicRepository topicRepository, AgentService agentService) {
        this.topicRepository = topicRepository;
        this.agentService = agentService;
    }

    public Topic getTopic(Long id) {
        return topicRepository.findById(id)
                .orElseThrow(() -> new TopicNotFoundException(id));
    }

    //todo pagination
    public List<Topic> getAllTopics(Long learnerId) {
        return topicRepository.findAllByLearnerId(learnerId);
    }

    public CreateTopicResponseDTO createTopic(CreateTopicRequestDTO createTopicDto, Learner learner) {
        var topicToCreate = new Topic(createTopicDto, learner.getId());
        var createdTopic = topicRepository.save(topicToCreate);
        log.info("Created topic {} with ID {} by learner with ID {}.", topicToCreate.getName(), topicToCreate.getId(), learner);
        if (createTopicDto.getAISuggestions()) {
            WordsSuggestionAgenticResponse suggestedWords;
            try {
                suggestedWords = agentService.getTopicWordsSuggestion(createTopicDto);
            } catch (AgenticException e) {
                throw new RuntimeException(e);
            }
            return new CreateTopicResponseDTO(createdTopic, suggestedWords);
        } else {
            return new CreateTopicResponseDTO(createdTopic);
        }
    }

    public Topic deleteTopic(Long id) {
        Topic topicToDelete = topicRepository.findById(id)
                .orElseThrow(() -> new TopicNotFoundException(id));
        topicRepository.deleteById(topicToDelete.getId());
        log.info("Topic {} with ID {} deleted including all associated words.", topicToDelete.getName(), topicToDelete.getId());
        return topicToDelete;
    }

    public Topic editTopic(Long id, String topic) {
        Topic topicToEdit = topicRepository.findById(id)
                .orElseThrow(() -> new TopicNotFoundException(id));
        topicToEdit.setName(topic);
        log.info("Topic with ID {} changed to {}.", topicToEdit.getId(), topicToEdit.getName());
        return topicRepository.save(topicToEdit);
    }
}
