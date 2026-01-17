package ai.pribela.glossapp.repository;

import ai.pribela.glossapp.repository.data.Word;
import org.springframework.data.repository.ListCrudRepository;

public interface WordRepository extends ListCrudRepository<Word, Long> {
}
