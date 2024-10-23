package ReviewsMS;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class Start_ReviewMS {
    public static void main(String[] args) {
        SpringApplication.run(Start_ReviewMS.class, args);
    }
}
