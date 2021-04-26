package pl.edu.agh.io.eventsOrganizer.errors;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Locale;

@AllArgsConstructor
@Data
public class ErrorInfo {

    private String pathInfo;

    private String method;

    private String queryString;

    private String protocol;

    private int contentLength;

    private Locale locale;

    private int localPort;

    private String contentType;
}
