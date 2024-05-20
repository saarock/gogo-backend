package gogo.school.com.services.implementaion.email;

import java.io.File;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import gogo.school.com.services.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmalServicesImpl implements EmailService {
    @Autowired
    private JavaMailSender mailSender;

    private Logger logger = LoggerFactory.getLogger(EmalServicesImpl.class);

    @Override
    public boolean sendEmail(String to, String subject, String message) {
        try {
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setTo(to);
            simpleMailMessage.setSubject(subject);
            simpleMailMessage.setText(message);
            simpleMailMessage.setFrom("saarock200@gmail.com");
            mailSender.send(simpleMailMessage);
            System.out.println("Email send to the " + to);
            logger.info("Email has been sent...");
            return true;
        } catch (Exception error) {
            logger.info(error.getMessage());
            return false;
        }
    }

    @Override
    public void sendEmail(String[] to, String subject, String message) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(to);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(message);
        simpleMailMessage.setFrom("saarock200@gmail.com");
        mailSender.send(simpleMailMessage);
        logger.info("Email has been sent");

    }

    @Override
    public void sendEmailWithHtml(String to, String subject, String htmlContext) {

        try {
            MimeMessage simpleMailMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(simpleMailMessage, true, "UTF-8");
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setFrom("saarock200@gmail.com");
            helper.setText(htmlContext, true);
            mailSender.send(simpleMailMessage);
            logger.info("Email has been sent");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void sendEmailWithFile(String to, String subject, String message, File file) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setSubject(subject);
            helper.setTo(to);
            helper.setFrom("saarock200@gmail.com");
            helper.setText(message);
            helper.addAttachment(file.getName(), file);
            mailSender.send(mimeMessage);
            System.out.println("Email with attachment sent successfully!");

        } catch (MessagingException e) {
            System.out.println("Failed to send email with attachment. Error: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

}
