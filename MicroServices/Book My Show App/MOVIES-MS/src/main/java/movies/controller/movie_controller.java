package movies.controller;

import movies.Dto.movie_DTO;
import movies.pojo.movie_reserve_pojo;
import movies.pojo.user_pojo;
import movies.service.movie_service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import screen.Dto.screen_DTO;

import java.util.List;

@RestController
@RequestMapping("/movie")
public class movie_controller {

    @Autowired
    private movie_service service;

    @GetMapping("/health")
    public String health(){
        return "--- Movie-MS is UP ---";
    }

    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody movie_DTO movie_data){
        try {
            service.save(movie_data);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/InHall/{movieName}")
    public ResponseEntity<?> setHAll(@RequestBody screen_DTO screen,@PathVariable("movieName") String movieName){
        try {
            service.set_hall_in_movie(screen,movieName);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/book/{movieName}")
    public ResponseEntity<?> book_Ticket(@PathVariable("movieName") String movieName){
        try{
            service.Search_Movie(movieName);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


    // client request

    @GetMapping("book_reserved")
    void Book_seats_that_are_reserved(@RequestBody movie_reserve_pojo movieReservePojo,@RequestBody user_pojo userPojo){
        try {
            service.Book_seats_that_are_reserved(userPojo, movieReservePojo);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
