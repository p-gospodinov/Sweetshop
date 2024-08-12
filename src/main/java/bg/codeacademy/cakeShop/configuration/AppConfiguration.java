package bg.codeacademy.cakeShop.configuration;

import bg.codeacademy.cakeShop.mapper.Mapper;
import bg.codeacademy.cakeShop.shedule.TransactionTaskExecutor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfiguration {
    @Bean
    public Mapper modelMapper(BCryptPasswordEncoder passwordEncoder) {
        return new Mapper(passwordEncoder);
    }

    @Bean
    public Thread reader(TransactionTaskExecutor exec) {
        Thread thread = new Thread(exec);
        thread.start();
        return thread;
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}