package user.controller;


import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import user.Clients.movie_client;
import user.Dto.user_DTO;
import user.pojo.user_pojo;
import user.service.User_Queries;
import user.service.user_service;
import user.service.user_service_2;

@Slf4j
@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "http://localhost:3000")
public class user_controller {

    @Autowired
    private movie_client  movieClientl;

    @Autowired
    private user_service service;

    @Autowired
    private User_Queries userQueries;

    @Autowired
    private user_service_2 service2;

    @GetMapping
    public ResponseEntity<?> Login(){
        return new ResponseEntity<>( " -- USER Logined Successfully --",HttpStatus.OK);
    }

    @PutMapping
    public void Update(@RequestBody user_DTO userDto){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        service.update_User(auth.getName(), userDto);
    }

    @GetMapping("/details")
    public user_pojo show_user(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return service.show_user(auth.getName());
    }

    @PutMapping("/book-reserved/{id}")
    public void Book_seats_that_are_reserved(@PathVariable ObjectId id){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        service.Book_seats_that_are_reserved(auth.getName() , id);
    }


    @DeleteMapping("/cancel-booked/{id}")
    public void Delete_Booked_ticket(@PathVariable ObjectId id) {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            service.Delete_Booked_ticket(auth.getName(), id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    // movie
    @GetMapping("/mh")
    public String movie_health(){
        try {
            String str = movieClientl.health();
            return str;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
