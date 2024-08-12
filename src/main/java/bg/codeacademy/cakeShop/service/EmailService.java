package bg.codeacademy.cakeShop.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.mail.javamail.JavaMailSender;


@Service
public class EmailService {
//    private final JavaMailSender javaMailSender;
//
//    public EmailService(JavaMailSender javaMailSender) {
//        this.javaMailSender = javaMailSender;
//    }
//    public String sendSimpleMail(EmailDetails details) {
//
//        try {
//            SimpleMailMessage mailMessage
//                    = new SimpleMailMessage();
//
//
//            mailMessage.setFrom("gospodinov.petar46@gmail.com");
//            mailMessage.setTo(details.getRecipient());
//            mailMessage.setText(details.getMsgBody());
//            mailMessage.setSubject(details.getSubject());
//
//            javaMailSender.send(mailMessage);
//            return "Mail Sent Successfully...";
//        }
//
//        catch (Exception e) {
//            return "Error while Sending Mail";
//        }
//    }

    @Autowired
    private JavaMailSender emailSender;

    public void sendSimpleMessage(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        emailSender.send(message);
    }

    public void sendHtmlMessage(String to, String subject, String htmlBody) throws MessagingException {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(htmlBody, true);

        emailSender.send(message);
    }

    public String generateHtmlContentCreateContract(String recipientName,String offerorEmail, String contractIdentifier) {
        return "<!DOCTYPE html>" +
                "<html>" +
                "<head>" +
                "<style>" +
                "body {font-family: Arial, sans-serif; margin: 0; padding: 0; background-color: #f4f4f4;}" +
                ".container {width: 100%; max-width: 600px; margin: 0 auto; background-color: #ffffff; padding: 20px; box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);}" +
                ".header {background-color: #007bff; color: #ffffff; padding: 10px; text-align: center;}" +
                ".content {padding: 20px;}" +
                ".footer {background-color: #007bff; color: #ffffff; text-align: center; padding: 10px; font-size: 12px;}" +
                "</style>" +
                "</head>" +
                "<body>" +
                "<div class='container'>" +
                "<div class='header'>" +
                "<h1>Contract</h1>" +
                "</div>" +
                "<div class='content'>" +
                "<p>Hello, " + recipientName + "!</p>" +
                "<p>This is a contract sent from an offeror with an email: "+offerorEmail+"!</p>" +
                "<p>If you are pleased with the details of contract with identifier: "+contractIdentifier+", please validate it!</p>" +
                "<p>Best regards!</p>" +
                "</div>" +
                "<div class='footer'>" +
                "&copy; 2024 Your Company. All rights reserved." +
                "</div>" +
                "</div>" +
                "</body>" +
                "</html>";
    }

    public String generateHtmlContentValidateContract(String recipientName,String offerorEmail, String contractIdentifier) {
        return "<!DOCTYPE html>" +
                "<html>" +
                "<head>" +
                "<style>" +
                "body {font-family: Arial, sans-serif; margin: 0; padding: 0; background-color: #f4f4f4;}" +
                ".container {width: 100%; max-width: 600px; margin: 0 auto; background-color: #ffffff; padding: 20px; box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);}" +
                ".header {background-color: #007bff; color: #ffffff; padding: 10px; text-align: center;}" +
                ".content {padding: 20px;}" +
                ".footer {background-color: #007bff; color: #ffffff; text-align: center; padding: 10px; font-size: 12px;}" +
                "</style>" +
                "</head>" +
                "<body>" +
                "<div class='container'>" +
                "<div class='header'>" +
                "<h1>Contract validation</h1>" +
                "</div>" +
                "<div class='content'>" +
                "<p>Hello, " + recipientName + "!</p>" +
                "<p>Contract with identifier: "+contractIdentifier+" has been validated successfully by "+ offerorEmail+"!</p>" +
                "<p>Best regards!</p>" +
                "</div>" +
                "<div class='footer'>" +
                "&copy; 2024 Your Company. All rights reserved." +
                "</div>" +
                "</div>" +
                "</body>" +
                "</html>";
    }
}
