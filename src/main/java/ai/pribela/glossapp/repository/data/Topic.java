package ai.pribela.glossapp.repository.data;

import ai.pribela.glossapp.dto.CreateTopicRequestDTO;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Topic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "source_language")
    private String sourceLanguage;

    @Column(name = "target_language")
    private String targetLanguage;

    @Column(name = "proficiency_level")
    private String proficiencyLevel;

    private Long learnerId;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY, mappedBy = "topic")
    private List<Word> words;

    public Topic(CreateTopicRequestDTO createTopicDto, Long learnerId) {
        this(createTopicDto.topic(), createTopicDto.description(), learnerId, createTopicDto.sourceLanguage(), createTopicDto.targetLanguage(), createTopicDto.proficiencyLevel());
    }

    public Topic(String name, String description, Long learnerId, String sourceLanguage, String targetLanguage, String proficiencyLevel) {
        this.name = name;
        this.description = description;
        this.learnerId = learnerId;
        this.words = List.of();
        this.sourceLanguage = sourceLanguage;
        this.targetLanguage = targetLanguage;
        this.proficiencyLevel = proficiencyLevel;
    }

    public void addWord(Word word) {
        if (words == null) {
            words = List.of();
        }
        words.add(word);
        word.setTopic(this);
    }

    public void addWords(List<Word> words) {
        words.forEach(this::addWord);
    }
}
