package ai.pribela.glossapp.exception;

public class TopicNotFoundException extends RuntimeException {

    private Long id;

    public TopicNotFoundException(Long id) {
        super("Topic with id '" + id + "' not found.");
    }
}
