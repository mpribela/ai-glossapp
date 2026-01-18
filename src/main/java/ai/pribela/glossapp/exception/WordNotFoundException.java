package ai.pribela.glossapp.exception;

public class WordNotFoundException extends RuntimeException {

    private Long id;

    public WordNotFoundException(Long id) {
        super("Word with id '" + id + "' not found.");
    }
}
