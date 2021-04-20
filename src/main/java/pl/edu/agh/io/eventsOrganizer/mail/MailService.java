package pl.edu.agh.io.eventsOrganizer.mail;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

public class MailService {
    final String username = "";
    final String password = "";
    Properties prop = createProperties();
    Session session = Session.getInstance(prop,
            new javax.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(username, password);
                }
            });

    public void sendEmail(Message message) throws MessagingException {
        Transport.send(message);
    }

    public Message createMessage(Mail mail) throws UnsupportedEncodingException, MessagingException {
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress("agheventsorganizer@interia.pl", "AGH Events Organizer"));
        message.setSubject(mail.getSubject());

        message.setRecipients(
                Message.RecipientType.TO,
                InternetAddress.parse(mail.getRecipient())
        );
        message.setText(mail.getContent());
        return message;
    }

    Properties createProperties() {
        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.mailgun.org");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true"); //TLS
        return prop;
    }
}
