package user.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import user.Dto.user_DTO;
import user.service.user_service;

@Slf4j
@RestController
@RequestMapping("/user")
public class user_controller {

    @Autowired
    private user_service service;

    @GetMapping
    public ResponseEntity<?> Login(){
        return new ResponseEntity<>( " -- USER Logined Successfully --",HttpStatus.OK);
    }
}
