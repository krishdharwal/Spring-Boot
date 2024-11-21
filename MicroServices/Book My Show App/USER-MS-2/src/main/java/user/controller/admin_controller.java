package user.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import user.Dto.user_DTO;
import user.pojo.user_pojo;
import user.service.User_Queries;
import user.service.user_service;

import java.util.List;

@RestController
@RequestMapping("/admin")
@Slf4j
public class admin_controller {

    @Autowired
    private user_service service;

    @Autowired
    private User_Queries userQueries;

    @GetMapping
    public ResponseEntity<?> Login(){
        return new ResponseEntity<>( " -- ADMIN Logined Successfully --",HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<?> save_ADMIN(@RequestBody user_DTO user){
        try{
            service.save_admin(user);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (Exception e){
            log.error(" -- error  in save in user controller --");
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }


    @GetMapping("/find/{name}")
    public user_pojo findByName(@PathVariable String name){
        try {
            return userQueries.findByName(name);
        }catch (Exception e){
            log.error("error in find by  name in user service ");
            return null;
        }
    }

    @GetMapping("/show-users")
    public ResponseEntity<?> showAll() {
        try {
            List<user_pojo> users = service.findAll();
            assert users != null;
            return new ResponseEntity<>(users, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_GATEWAY);
        }
    }

}
