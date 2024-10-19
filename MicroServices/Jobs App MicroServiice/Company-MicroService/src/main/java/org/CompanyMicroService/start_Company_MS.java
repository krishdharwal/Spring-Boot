package org.CompanyMicroService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

@SpringBootApplication
@EnableFeignClients
public class start_Company_MS {

    public static void main(String[] args) {
        SpringApplication.run(start_Company_MS.class,args);
    }

    @Bean
    public PlatformTransactionManager transactionManager(MongoDatabaseFactory factory){
        return new MongoTransactionManager(factory);
    }
}
