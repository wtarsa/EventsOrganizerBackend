package pl.edu.agh.io.eventsOrganizer.errors;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@Getter
@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class MailException extends RuntimeException {
    private final String path;
    private final List<String> details;

    public MailException(String exception, String path, List<String> details) {
        super(exception);
        this.path = path;
        this.details = details;
    }
}
