package user.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import user.Dto.user_DTO;
import user.service.User_Queries;
import user.service.user_service;

@RestController
@RequestMapping("/public")
@Slf4j
public class public_controller {

    @Autowired
    private user_service service;

    @Autowired
    private User_Queries userQueries;


    @GetMapping("/up")
    public String status(){
        return " -- user service layer is up and running -- ";
    }

    @PostMapping("/user")
    public ResponseEntity<?> save_USER(@RequestBody user_DTO user){
        try{
            service.save_user(user);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (Exception e){
            log.error(" -- error  in save in user controller --");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}
