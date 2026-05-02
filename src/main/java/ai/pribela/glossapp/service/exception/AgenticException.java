package ai.pribela.glossapp.service.exception;

import lombok.Getter;

import java.util.List;

@Getter
public class AgenticException extends Exception {

    private final List<String> exceptions;

    public AgenticException(List<Throwable> exceptions) {
        this.exceptions = exceptions.stream().map(Throwable::getMessage).toList();
        super();
    }
}
