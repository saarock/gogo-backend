package gogo.school.com;

import java.io.File;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import gogo.school.com.services.EmailService;


@SpringBootTest
public class EmailSenderTest {
    @Autowired
    private EmailService emailService;

    @Test
    void emailSenderTest() {
        System.out.println("Sending email");
        emailService.sendEmail("saarock4646@gmail.com", "THis is the email from spring boot", "Hello aayush basnet");
    }

    @Test
    void sendHTMLInMail() {
        String htmlContent = "<!DOCTYPE html>\n<html lang=\"en\">\n<head>\n<meta charset=\"UTF-8\">\n<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n<title>Beautiful Email</title>\n<style>body {font-family: Arial, sans-serif;background-color: #f3f3f3;margin: 0;padding: 0;line-height: 1.6;}.container {max-width: 600px;margin: 20px auto;padding: 20px;background-color: #fff;border-radius: 10px;box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);}.h1 {color: #333;text-align: center;}.p {color: #666;font-size: 16px;line-height: 1.6;margin-bottom: 20px;}.btn {display: inline-block;padding: 10px 20px;background-color: green;color: #fff;text-decoration: none;border-radius: 5px;font-weight: bold;transition: background-color 0.3s ease;}.btn:hover {background-color: #0056b3;}.footer {margin-top: 20px;text-align: center;color: #999;}</style>\n</head>\n<body>\n<div class=\"container\">\n<h1>Welcome to Our Newsletter</h1>\n<p>Dear [Recipient Name],</p>\n<p>We are thrilled to have you join our community! You are now subscribed to receive the latest updates, news, and special offers.</p>\n<p>Stay tuned for exciting content coming your way!</p>\n<a href=\"#\" class=\"btn\">Read Our Latest Blog</a>\n<div class=\"footer\">\n<p>If you have any questions or concerns, feel free to <a href=\"#\">contact us</a>.</p>\n<p>This email was sent to you by Beautiful Company.</p>\n</div>\n</div>\n</body>\n</html>";

        emailService.sendEmailWithHtml("chaudharyak2080@gmail.com", "You are hired!", htmlContent);
    }

    @Test
    void sendMailWithFile() {
        // File file = new
        // File("/home/codehons/tech/java/springboot/email/mail-sender/src/main/resources/static/images/download.jpeg");

        emailService.sendEmailWithFile("saarock4646@gmail.com", "This message from the spring boot file.",
                "Helllo aayush vai", new File(
                        "/home/codehons/tech/java/springboot/email/mail-sender/src/main/resources/static/images/download.jpeg"));
    }
}