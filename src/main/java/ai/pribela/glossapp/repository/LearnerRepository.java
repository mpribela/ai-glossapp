package ai.pribela.glossapp.repository;

import ai.pribela.glossapp.repository.data.Learner;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface LearnerRepository extends CrudRepository<Learner, Long> {
    Optional<Learner> findByUsername(String username);
}
