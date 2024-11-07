package user.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import user.Dto.user_DTO;
import user.service.user_service;

@Slf4j
@RestController
@RequestMapping("/user")
public class user_controller {

    @Autowired
    private user_service service;


    @PostMapping
    public ResponseEntity<?> save(@RequestBody user_DTO user){
        try{
            service.save(user);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (Exception e){
            log.error(" -- error  in save in user controller --");
            return new ResponseEntity<>(HttpStatus.OK);
        }

    }
}
