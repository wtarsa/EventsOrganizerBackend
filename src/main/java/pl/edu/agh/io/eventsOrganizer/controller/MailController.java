package pl.edu.agh.io.eventsOrganizer.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.agh.io.eventsOrganizer.errors.MailException;
import pl.edu.agh.io.eventsOrganizer.mail.Mail;
import pl.edu.agh.io.eventsOrganizer.mail.MailService;
import pl.edu.agh.io.eventsOrganizer.model.Classes;
import pl.edu.agh.io.eventsOrganizer.model.Instructor;
import pl.edu.agh.io.eventsOrganizer.repository.ClassesRepository;
import pl.edu.agh.io.eventsOrganizer.repository.InstructorRepository;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/mail")
public class MailController {

    private MailService mailService = new MailService();

    private final InstructorRepository instructorRepository;

    private final ClassesRepository classesRepository;

    private final static ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

    private final static Logger logger = LoggerFactory.getLogger(MailController.class);

    public MailController(InstructorRepository instructorRepository, ClassesRepository classesRepository) {
        this.instructorRepository = instructorRepository;
        this.classesRepository = classesRepository;
        executorService.scheduleAtFixedRate(this::sendReminders, 0, 1, TimeUnit.DAYS);
    }

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

    @CrossOrigin
    @GetMapping("/remind")
    public ResponseEntity<List<Mail>> sendMailsWithReminders() {
        List<Mail> mailsSent = sendReminders();
        return new ResponseEntity<>(mailsSent, HttpStatus.OK);
    }

    //helper methods

    private void mailSender(Mail mail) {
        try {
            mailService.sendEmail(mail);
        } catch (MessagingException | UnsupportedEncodingException e) {
            logger.info("Error when sending mail! ", e);
        }
    }

    private List<Mail> sendReminders() {
        List<Mail> mailsSent = new ArrayList<>();
        List<Instructor> instructorList = instructorRepository.findAll();
        List<List<Classes>> instructorClasses = instructorList.stream()
                .map(ins -> classesRepository.findClassesByInstructorId(ins.getId()))
                .map(classesList -> classesList.stream()
                        .filter(classes -> classes.getStartTime().isAfter(LocalDateTime.now()))
                        .filter(classes -> classes.getEndTime().isBefore(LocalDateTime.now().plusWeeks(1)))
                        .collect(Collectors.toList()))
                .collect(Collectors.toList());

        instructorClasses.forEach(classesList -> {
            if (!classesList.isEmpty()) {
                StringBuilder builder = new StringBuilder();
                builder.append("You have upcoming events next week:\n");
                classesList.forEach(classes -> builder.append(classes.getReminderNote()));
                Mail mail = new Mail(classesList.get(0).getInstructor().getEmail(), "Timetable reminder!", builder.toString());
                mailSender(mail);
                mailsSent.add(mail);
            }
        });
        return mailsSent;
    }
}
