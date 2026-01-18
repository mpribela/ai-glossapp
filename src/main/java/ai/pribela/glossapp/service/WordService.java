package ai.pribela.glossapp.service;

import ai.pribela.glossapp.dto.WordDto;
import ai.pribela.glossapp.exception.TopicNotFoundException;
import ai.pribela.glossapp.exception.WordNotFoundException;
import ai.pribela.glossapp.repository.TopicRepository;
import ai.pribela.glossapp.repository.WordRepository;
import ai.pribela.glossapp.repository.data.Topic;
import ai.pribela.glossapp.repository.data.Word;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class WordService {

    private final TopicRepository topicRepository;
    private final WordRepository wordRepository;

    public WordService(TopicRepository topicRepository, WordRepository wordRepository) {
        this.topicRepository = topicRepository;
        this.wordRepository = wordRepository;
    }

    public Word getWord(Long topicId, Long wordId) {
        topicRepository.findById(topicId)
                .orElseThrow(() -> new TopicNotFoundException(topicId));
        return wordRepository.findById(wordId)
                .orElseThrow(() -> new WordNotFoundException(wordId));
    }

    //todo pagination
    public List<Word> getAllWords(Long topicId) {
        topicRepository.findById(topicId)
                .orElseThrow(() -> new TopicNotFoundException(topicId));
        return wordRepository.findByTopicId(topicId);
    }

    public Word createWord(Long topicId, WordDto createWordDto) {
        Topic topic = topicRepository.findById(topicId)
                .orElseThrow(() -> new TopicNotFoundException(topicId));
        Word wordToCreate = Word.builder().text(createWordDto.text()).topic(topic).build();
        wordToCreate = wordRepository.save(wordToCreate);
        log.info("Created word {} with ID {}.", wordToCreate.getText(), wordToCreate.getId());
        return wordToCreate;
    }

    public Word deleteWord(Long topicId, Long wordId) {
        topicRepository.findById(topicId)
                .orElseThrow(() -> new TopicNotFoundException(topicId));
        Word wordToDelete = wordRepository.findById(wordId)
                .orElseThrow(() -> new WordNotFoundException(wordId));
        wordRepository.delete(wordToDelete);
        log.info("Word {} with ID {} deleted.", wordToDelete.getText(), wordToDelete.getId());
        return wordToDelete;
    }

    public Word editWord(Long topicId, Long wordId, String word) {
        topicRepository.findById(topicId)
                .orElseThrow(() -> new TopicNotFoundException(topicId));
        Word wordToEdit = wordRepository.findById(wordId)
                .orElseThrow(() -> new WordNotFoundException(wordId));
        wordToEdit.setText(word);
        log.info("Word with ID {} changed to {}.", wordToEdit.getId(), wordToEdit.getText());
        return wordRepository.save(wordToEdit);
    }
}
