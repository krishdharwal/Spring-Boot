package Mail;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@SpringBootApplication
public class Start_Mail {
    public static void main(String[] args) {
        SpringApplication.run(Start_Mail.class,args);
    }

    @Bean
    private JavaMailSender sender(){
        return new JavaMailSenderImpl();
    }
}
