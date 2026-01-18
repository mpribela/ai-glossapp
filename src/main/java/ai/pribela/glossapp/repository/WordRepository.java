package ai.pribela.glossapp.repository;

import ai.pribela.glossapp.repository.data.Word;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;

public interface WordRepository extends ListCrudRepository<Word, Long> {
    List<Word> findByTopicId(Long id);
}
