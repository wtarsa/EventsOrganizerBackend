package pl.edu.agh.io.eventsOrganizer.mail;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.Properties;
import java.util.logging.Logger;

public class MailService {
    final String user = "postmaster@sandbox349dce3";
    final String name = "e7ab14b6ab5bb5e79385e83ea.mailgun.org";
    final String username = user + name;

    final String pass = "16bc7710124d6e3df5a6";
    final String word = "61b6ca10a024-71b35d7e-f9882ae5";
    final String password = pass + word;

    Properties prop = createProperties();
    Session session = Session.getInstance(prop,
            new javax.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(username, password);
                }
            });

    public void sendEmail(Mail mail) throws MessagingException, UnsupportedEncodingException {
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress("agheventsorganizer@interia.pl", "AGH Events Organizer"));
        message.setSubject(mail.getSubject());

        message.setRecipients(
                Message.RecipientType.TO,
                InternetAddress.parse(mail.getRecipient())
        );
        message.setText(mail.getContent());
        Logger.getLogger("").info("Sending email to " + mail.getRecipient() + " subject: " + mail.getSubject());
        Transport.send(message);
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
