package pl.edu.agh.io.eventsOrganizer.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.edu.agh.io.eventsOrganizer.errors.ErrorInfo;

import javax.servlet.http.HttpServletRequest;

@Controller
public class CustomErrorController implements ErrorController {

    /**
     * Handles server errors.
     *
     * @param request
     * @return error
     */
    @RequestMapping("/error")
    public ResponseEntity<ErrorInfo> handleError(HttpServletRequest request) {
        return new ResponseEntity<>(
                new ErrorInfo(request.getPathInfo(), request.getMethod(), request.getQueryString(), request.getProtocol(),
                        request.getContentLength(), request.getLocale(), request.getLocalPort(), request.getContentType()),
                HttpStatus.NOT_FOUND);
    }

    @Override
    public String getErrorPath() {
        return null;
    }
}

