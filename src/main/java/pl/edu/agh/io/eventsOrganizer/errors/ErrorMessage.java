package pl.edu.agh.io.eventsOrganizer.errors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ErrorMessage {
    private String error;
    private List<String> details;
    private LocalDateTime timestamp;
    private HttpStatus httpStatus;
    private String path;
}
