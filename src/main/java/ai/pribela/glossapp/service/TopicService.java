package ai.pribela.glossapp.service;

import ai.pribela.glossapp.dto.TopicDto;
import ai.pribela.glossapp.exception.TopicNotFoundException;
import ai.pribela.glossapp.repository.TopicRepository;
import ai.pribela.glossapp.repository.data.Topic;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TopicService {

    private final TopicRepository topicRepository;

    public TopicService(TopicRepository topicRepository) {
        this.topicRepository = topicRepository;
    }

    public Topic createTopic(TopicDto createTopicDto) {
        Topic topicToCreate = new Topic(createTopicDto.topic());
        return topicRepository.save(topicToCreate);
    }

    public Topic deleteTopic(Long id) {
        Topic topicToDelete = topicRepository.findById(id)
                .orElseThrow(() -> new TopicNotFoundException(id));
        topicRepository.deleteById(topicToDelete.getId());
        return topicToDelete;
    }

    public Topic editTopic(Long id, String topic) {
        Topic topicToEdit = topicRepository.findById(id)
                .orElseThrow(() -> new TopicNotFoundException(id));
        topicToEdit.setName(topic);
        return topicRepository.save(topicToEdit);
    }

    public Topic getTopic(Long id) {
        return topicRepository.findById(id)
                .orElseThrow(() -> new TopicNotFoundException(id));
    }

    //todo pagination
    public List<Topic> getAllTopics() {
        return topicRepository.findAll();
    }
}
