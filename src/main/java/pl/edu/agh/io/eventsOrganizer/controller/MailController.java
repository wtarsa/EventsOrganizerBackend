package pl.edu.agh.io.eventsOrganizer.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.agh.io.eventsOrganizer.errors.MailException;
import pl.edu.agh.io.eventsOrganizer.mail.Mail;
import pl.edu.agh.io.eventsOrganizer.mail.MailService;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.List;

@RestController
@RequestMapping("/mail")
public class MailController {

    private MailService mailService = new MailService();

    @CrossOrigin
    @PostMapping
    public ResponseEntity<Mail> sendMail(@RequestBody Mail mail, HttpServletRequest request) throws UnsupportedEncodingException {
        try {
            mailService.sendEmail(mail);
            return new ResponseEntity<>(mail, HttpStatus.OK);
        } catch (MessagingException e) {
            throw new MailException(
                    "There was an error sending the email",
                    request.getRequestURI(),
                    List.of("There was an error sending the email")
            );
        }
    }
}
