package br.com.yuri.todomaneiro.config;

import br.com.yuri.todomaneiro.service.email.IEmailService;
import br.com.yuri.todomaneiro.service.email.SmtpEmailService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("hom")
public class HomConfig {

    @Bean
    public IEmailService emailService() {
        return new SmtpEmailService();
    }

}
