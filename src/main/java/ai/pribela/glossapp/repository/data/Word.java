package ai.pribela.glossapp.repository.data;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Word {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String text;

    private String notes;

    private String translation;

    private String type;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "attributes", columnDefinition = "jsonb")
    private Map<String, Object> attributes;

    @OneToMany(mappedBy = "word", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private List<Sentence> sentences;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "topic_id", nullable = false)
    private Topic topic;

    public Word(String text, String translation, String type, Map<String, Object> attributes, Topic topic, List<Sentence> sentences) {
        this.text = text;
        this.translation = translation;
        this.type = type;
        this.attributes = attributes;
        this.topic = topic;
        addSentences(sentences);
    }

    public void addSentence(Sentence sentence) {
        if (sentences == null) {
            sentences = new ArrayList<>();
        }
        sentences.add(sentence);
        sentence.setWord(this);
    }

    public void addSentences(List<Sentence> sentences) {
        sentences.forEach(this::addSentence);
    }
}
