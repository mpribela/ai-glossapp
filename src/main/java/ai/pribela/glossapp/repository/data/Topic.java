package ai.pribela.glossapp.repository.data;

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

    private Long learnerId;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY, mappedBy = "topic")
    private List<Word> words;

    public Topic(String name, Long learnerId) {
        this.name = name;
        this.learnerId = learnerId;
    }

    public void addWord(Word word) {
        words.add(word);
        word.setTopic(this);
    }

}
