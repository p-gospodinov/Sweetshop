package bg.codeacademy.cakeShop;

import bg.codeacademy.cakeShop.shedule.TransactionTaskExecutor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class CakeShopApplication {
//    private static ApplicationContext context = null;
//    public CakeShopApplication(ApplicationContext context) {
//       CakeShopApplication.context = context;
//    }
    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(CakeShopApplication.class, args);
        TransactionTaskExecutor taskExecutor = context.getBean(TransactionTaskExecutor.class);
        taskExecutor.start();
//        EmailDetails details = new EmailDetails("petargospodinov06@gmail.com","Contract with number ..... has been sent!", "Contract");
//        EmailController emailController = context.getBean(EmailController.class);
//        emailController.sendMail(details);
//        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
//        EmailServiceImpl emailService = new EmailServiceImpl(mailSender);
//        EmailDetails emailDetails = new EmailDetails("petargospodinov06@gmail.com","<p>Contract have been sent.</p><p>Please sign the contract.</p>", "Contract");
//        try {
//            emailService.sendSimpleMail(emailDetails);
//            System.out.println("Email sent successfully.");
//        } catch (MessagingException | UnsupportedEncodingException e) {
//            System.out.println("Failed to send email. Error: " + e.getMessage());
//        }
    }
}
