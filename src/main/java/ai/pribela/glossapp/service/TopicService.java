package ai.pribela.glossapp.service;

import ai.pribela.glossapp.dto.CreateTopicRequestDTO;
import ai.pribela.glossapp.dto.GetTopicDetailResponseDTO;
import ai.pribela.glossapp.dto.agentic.WordsSuggestionAgenticResponseDTO;
import ai.pribela.glossapp.dto.transformer.TopicDetailTransformer;
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
    private final TopicDetailTransformer topicDetailTransformer;

    public TopicService(TopicRepository topicRepository, AgentService agentService, TopicDetailTransformer topicDetailTransformer) {
        this.topicRepository = topicRepository;
        this.agentService = agentService;
        this.topicDetailTransformer = topicDetailTransformer;
    }

    public GetTopicDetailResponseDTO getTopic(Long id, Learner learner) {
        var topic = topicRepository.findByIdAndLearnerId(id, learner.getId())
                .orElseThrow(() -> new TopicNotFoundException(id));
        return topicDetailTransformer.getTopicDetailResponseDTO(topic);
    }

    //todo pagination
    public List<Topic> getAllTopics(Long learnerId) {
        return topicRepository.findAllByLearnerId(learnerId);
    }

    public void createTopic(CreateTopicRequestDTO createTopicDto, Learner learner) {
        var topicToCreate = new Topic(createTopicDto, learner.getId());
        if (createTopicDto.getAISuggestions()) {
            WordsSuggestionAgenticResponseDTO suggestedWords;
            try {
                suggestedWords = agentService.getTopicWordsSuggestion(createTopicDto);
                topicToCreate.addWords(suggestedWords);
            } catch (AgenticException e) {
                log.error("Error while getting topic words suggestion.", e);
            }
        }
        var createdTopic = topicRepository.save(topicToCreate);
        log.info("Created topic {} with ID {} by learner with ID {}.", createdTopic.getName(), createdTopic.getId(), learner);
    }

    public Topic deleteTopic(Long id, Learner learner) {
        Topic topicToDelete = topicRepository.findByIdAndLearnerId(id, learner.getId())
                .orElseThrow(() -> new TopicNotFoundException(id));
        topicRepository.deleteById(topicToDelete.getId());
        log.info("Topic {} with ID {} deleted including all associated words.", topicToDelete.getName(), topicToDelete.getId());
        return topicToDelete;
    }

    public Topic editTopic(Long id, String topic, Learner learner) {
        Topic topicToEdit = topicRepository.findByIdAndLearnerId(id, learner.getId())
                .orElseThrow(() -> new TopicNotFoundException(id));
        topicToEdit.setName(topic);
        log.info("Topic with ID {} changed to {}.", topicToEdit.getId(), topicToEdit.getName());
        return topicRepository.save(topicToEdit);
    }
}
