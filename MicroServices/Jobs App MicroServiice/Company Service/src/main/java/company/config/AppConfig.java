package company.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfig {

    // Now i am not using restTemplate to send request across sites
    // instead of i am using OPEN FEIGN
    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
