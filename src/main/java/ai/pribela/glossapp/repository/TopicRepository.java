package ai.pribela.glossapp.repository;

import ai.pribela.glossapp.repository.data.Topic;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;
import java.util.Optional;

public interface TopicRepository extends ListCrudRepository<Topic, Long> {
    List<Topic> findAllByLearnerId(Long learnerId);
}
