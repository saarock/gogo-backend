package gogo.school.com.services;

import java.io.File;

public interface EmailService {

    public boolean sendEmail(String to, String subject, String message);

    public void sendEmail(String[] to, String subject, String message);

    public void sendEmailWithHtml(String to, String subject, String htmlContext);

    public void sendEmailWithFile(String to, String subject, String message, File file);

}