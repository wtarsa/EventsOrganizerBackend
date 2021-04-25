package pl.edu.agh.io.eventsOrganizer.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

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
    public ResponseEntity<String> handleError(HttpServletRequest request) {
        return new ResponseEntity<>(
                "{\"pathInfo\": \" " + request.getPathInfo() + "  \"," +
                        "\"method\": \" " + request.getMethod() + "  \"," +
                        "\"queryString\": \" " + request.getQueryString() + "  \"}",
                HttpStatus.NOT_FOUND);
    }

    @Override
    public String getErrorPath() {
        return null;
    }
}

