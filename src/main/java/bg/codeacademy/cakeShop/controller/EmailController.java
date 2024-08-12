package bg.codeacademy.cakeShop.controller;

import bg.codeacademy.cakeShop.model.EmailDetails;
import bg.codeacademy.cakeShop.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class EmailController {
@Autowired
private EmailService emailService;

    @PostMapping("/send-mail")
    public String sendMail(@RequestParam String to, @RequestParam String subject, @RequestParam String text) {
        emailService.sendSimpleMessage(to, subject, text);
        return "Email sent successfully!";
    }
}
