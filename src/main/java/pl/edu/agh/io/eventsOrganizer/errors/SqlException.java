package pl.edu.agh.io.eventsOrganizer.errors;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@Getter
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class SqlException extends Exception {
    private final String path;
    private final List<String> details;

    public SqlException(String exception, String path, List<String> details) {
        super(exception);
        this.path = path;
        this.details = details;
    }
}
