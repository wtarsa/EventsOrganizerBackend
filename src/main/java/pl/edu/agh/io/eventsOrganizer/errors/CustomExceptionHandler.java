package pl.edu.agh.io.eventsOrganizer.errors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@SuppressWarnings({"unchecked", "rawtypes"})
@ControllerAdvice
class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {
        logger.error(ex.getLocalizedMessage());
        return new ResponseEntity(ex.getLocalizedMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(NotFoundException.class)
    public final ResponseEntity<Object> handleNotFoundException(NotFoundException ex) {
        logger.error(ex.getLocalizedMessage());
        ErrorMessage errorMessage = new ErrorMessage(
                ex.getLocalizedMessage(),
                ex.getDetails(),
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND,
                ex.getPath()
        );
        return new ResponseEntity(errorMessage, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MailException.class)
    public final ResponseEntity<Object> handleMailException(MailException ex) {
        logger.error(ex.getLocalizedMessage());
        ErrorMessage errorMessage = new ErrorMessage(
                ex.getLocalizedMessage(),
                ex.getDetails(),
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST,
                ex.getPath()
        );
        return new ResponseEntity(errorMessage, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(SqlException.class)
    public final ResponseEntity<Object> handleSqlException(SqlException ex) {
        logger.error(ex.getLocalizedMessage());
        ErrorMessage errorMessage = new ErrorMessage(
                ex.getLocalizedMessage(),
                ex.getDetails(),
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST,
                ex.getPath()
        );
        return new ResponseEntity(errorMessage, HttpStatus.BAD_REQUEST);
    }

}