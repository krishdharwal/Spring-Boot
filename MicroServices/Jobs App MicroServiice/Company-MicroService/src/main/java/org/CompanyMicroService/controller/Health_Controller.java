package org.CompanyMicroService.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/health")
public class Health_Controller {

    @GetMapping
    public String health(){
        return " -- jarvis -> Company Micro service app is ready sir ! --";
    }

}
