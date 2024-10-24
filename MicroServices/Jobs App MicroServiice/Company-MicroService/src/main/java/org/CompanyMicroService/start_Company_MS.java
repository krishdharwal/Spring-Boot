package org.CompanyMicroService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class start_Company_MS {

    public static void main(String[] args) {
        SpringApplication.run(start_Company_MS.class,args);
    }



}
