package user.controller;


import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import user.Dto.user_DTO;
import user.service.User_Queries;
import user.service.user_service;

@Slf4j
@RestController
@RequestMapping("/user")
public class user_controller {

    @Autowired
    private user_service service;

    @Autowired
    private User_Queries userQueries;

    @GetMapping
    public ResponseEntity<?> Login(){
        return new ResponseEntity<>( " -- USER Logined Successfully --",HttpStatus.OK);
    }

    @PutMapping
    public void Update(@RequestBody user_DTO userDto){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        service.update_User(auth.getName(), userDto);

    }

    @DeleteMapping
    public void delete(){
    }


    @GetMapping("/book-reserved/{id}")
    public void Book_seats_that_are_reserved(@PathVariable ObjectId id){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        service.Book_seats_that_are_reserved(auth.getName() , id);
    }

}
