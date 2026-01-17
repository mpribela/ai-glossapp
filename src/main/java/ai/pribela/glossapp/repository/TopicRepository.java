package ai.pribela.glossapp.repository;

import ai.pribela.glossapp.repository.data.Topic;
import org.springframework.data.repository.ListCrudRepository;

import java.util.Optional;

public interface TopicRepository extends ListCrudRepository<Topic, Long> {

    Optional<Topic> findByName(String name);
}
