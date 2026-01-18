package ai.pribela.glossapp.service;

import ai.pribela.glossapp.dto.TopicDto;
import ai.pribela.glossapp.exception.TopicNotFoundException;
import ai.pribela.glossapp.repository.TopicRepository;
import ai.pribela.glossapp.repository.data.Topic;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class TopicService {

    private final TopicRepository topicRepository;

    public TopicService(TopicRepository topicRepository) {
        this.topicRepository = topicRepository;
    }

    public Topic getTopic(Long id) {
        return topicRepository.findById(id)
                .orElseThrow(() -> new TopicNotFoundException(id));
    }

    //todo pagination
    public List<Topic> getAllTopics() {
        return topicRepository.findAll();
    }

    public Topic createTopic(TopicDto createTopicDto) {
        Topic topicToCreate = new Topic(createTopicDto.topic());
        topicToCreate = topicRepository.save(topicToCreate);
        log.info("Created topic {} with ID {}.", topicToCreate.getName(), topicToCreate.getId());
        return topicToCreate;
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
