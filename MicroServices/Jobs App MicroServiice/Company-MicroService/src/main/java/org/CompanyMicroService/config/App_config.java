package org.CompanyMicroService.config;

import org.modelmapper.ModelMapper;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.web.client.RestTemplate;

@Configuration
public class App_config {

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }

    @Bean
    public ServerCodecConfigurer serverCodecConfigurer() {
        return ServerCodecConfigurer.create();
    }
}
