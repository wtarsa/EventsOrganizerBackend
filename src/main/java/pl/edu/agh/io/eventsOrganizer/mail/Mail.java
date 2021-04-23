package pl.edu.agh.io.eventsOrganizer.mail;

import lombok.Getter;

@Getter
public class Mail {
    private final String recipient;
    private final String subject;
    private final String content;

    public Mail(String recipient, String subject, String content) {
        this.recipient = recipient;
        this.subject = subject;
        this.content = content;
    }
}
