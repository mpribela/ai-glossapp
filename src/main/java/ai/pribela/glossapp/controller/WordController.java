package ai.pribela.glossapp.controller;

import ai.pribela.glossapp.dto.WordDto;
import ai.pribela.glossapp.dto.WordListDto;
import ai.pribela.glossapp.repository.data.Word;
import ai.pribela.glossapp.service.WordService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/topic/{topicId}/word")
public class WordController {

    private final WordService wordService;

    public WordController(WordService wordService) {
        this.wordService = wordService;
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public WordListDto getWords(@PathVariable("topicId") Long topicId) {
        List<Word> words = wordService.getAllWords(topicId);
        return new WordListDto(words.stream()
                .map(WordDto::new)
                .toList());
    }

    @GetMapping("/{wordId}")
    @ResponseStatus(HttpStatus.OK)
    public WordDto getWord(@PathVariable("topicId") Long topicId, @PathVariable("wordId") Long wordId) {
        Word word = wordService.getWord(topicId, wordId);
        return new WordDto(word);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public WordDto createWord(@PathVariable("topicId") Long topicId, @Valid @RequestBody WordDto createWordDto) {
        Word word = wordService.createWord(topicId, createWordDto);
        return new WordDto(word);
    }

    @PutMapping("/{wordId}")
    @ResponseStatus(HttpStatus.OK)
    public WordDto editWord(@PathVariable("topicId") Long topicId, @PathVariable("wordId") Long wordId, @RequestBody WordDto editWordDto) {
        Word editedWord = wordService.editWord(topicId, wordId, editWordDto.text());
        return new WordDto(editedWord);
    }

    @DeleteMapping("/{wordId}")
    @ResponseStatus(HttpStatus.OK)
    public WordDto deleteWord(@PathVariable("topicId") Long topicId, @PathVariable("wordId") Long wordId) {
        Word deletedWord = wordService.deleteWord(topicId, wordId);
        return new WordDto(deletedWord);
    }

}
