package bg.codeacademy.cakeShop.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
public class ExcludeConfiguration {
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring()
                .requestMatchers(AntPathRequestMatcher.antMatcher("/h2-console/**"))
                .requestMatchers(AntPathRequestMatcher.antMatcher("/api/v1/legal-entities"))
                .requestMatchers(AntPathRequestMatcher.antMatcher("/api/v1/comments"))
                .requestMatchers(AntPathRequestMatcher.antMatcher("/api/v1/conversions"))
                .requestMatchers(AntPathRequestMatcher.antMatcher("/api/v1/send-mail"))
                .requestMatchers(AntPathRequestMatcher.antMatcher("/api/v1/purchases"));
    }
}
