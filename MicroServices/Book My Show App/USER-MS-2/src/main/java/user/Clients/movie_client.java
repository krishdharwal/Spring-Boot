package user.Clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import user.Dto.movie_reserve_dto;
import user.Dto.user_DTO;
import user.pojo.movie_reserve_pojo;

@FeignClient(name = "movieClient" , url = "http://localhost:9091/movie")
public interface movie_client {

    @PutMapping("/book-reserved")
     void Book_seats_that_are_reserved(@RequestBody movie_reserve_pojo movieReservePojo);
//solve this
//    , @RequestBody user_DTO userPojo
    @DeleteMapping("/cancel-booked")
     void Delete_Booked_ticket(@RequestBody movie_reserve_dto movieReserveDto);


    @GetMapping("/health")
     String health();


//    @PostMapping("/save")
//    public ResponseEntity<?> save(@RequestBody movie_DTO movie_data);


//    @PostMapping("/InHall/{movieName}")
//    public ResponseEntity<?> setHAll(@RequestBody screen_DTO screen,@PathVariable("movieName") String movieName);
//

    @GetMapping("/book/{movieName}")
     ResponseEntity<?> book_Ticket(@PathVariable("movieName") String movieName , @RequestBody user_DTO user);


    // client request to book the reserved seats
//    @PutMapping("/book-reserved")
//     void Book_seats_that_are_reserved(@RequestBody movie_reserve_pojo movieReservePojo,@RequestBody user_DTO userDto);
//

    }
