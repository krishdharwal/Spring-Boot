package company;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.loadbalancer.config.LoadBalancerAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@SpringBootApplication(exclude = { LoadBalancerAutoConfiguration.class })
@EnableFeignClients
public class start_company {

    public static void main(String[] args) {
        SpringApplication.run(start_company.class,args);
    }

}
