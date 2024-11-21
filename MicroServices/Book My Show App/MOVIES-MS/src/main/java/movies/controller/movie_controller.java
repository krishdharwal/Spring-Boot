package movies.controller;


import movies.Dto.movie_DTO;
import movies.Dto.movie_reserve_dto;
import movies.Dto.user_DTO;
import movies.pojo.movie_reserve_pojo;
import movies.service.movie_service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import movies.Dto.screen_DTO;

@RestController
@RequestMapping("/movie")
public class movie_controller {

    @Autowired
    private movie_service service;

    @GetMapping("/health")
    public String health(){
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return "--- Movie-MS is UP ---" ;
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
    ResponseEntity<?> book_Ticket(@PathVariable("movieName") String movieName , @RequestBody user_DTO user){
        try{
            service.Search_Movie(movieName, user);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // client request to book the reserved seats
    @PutMapping("/book-reserved")
    public void Book_seats_that_are_reserved(@RequestBody movie_reserve_pojo movieReservePojo,@RequestBody user_DTO userDto){
        try {
            service.Book_seats_that_are_reserved(userDto, movieReservePojo);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @DeleteMapping("/cancel-booked")
    public void Delete_Booked_ticket(@RequestBody movie_reserve_dto movieReserveDto){
        try{
            service.Delete_Booked_ticket(movieReserveDto);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
