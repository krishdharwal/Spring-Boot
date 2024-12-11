package movies.controller;

import movies.Dto.movie_DTO;
import movies.Dto.screen_DTO;
import movies.Dto.user_DTO;
import movies.pojo.movie_pojo;
import movies.service.movie_service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/movie")
public class movie_controller {

    @Autowired
    private movie_service service;

    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> health() {
        return ResponseEntity.ok(Map.of("status", "--- Movie-MS is UP ---"));
    }

    @PostMapping("/save")
    public ResponseEntity<Map<String, String>> saveMovie(@RequestBody movie_DTO movieData) {
        try {
            Map<String, String> response = service.save(movieData);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/InHall/{movieName}")
    public ResponseEntity<Map<String, String>> setHallForMovie(@RequestBody screen_DTO screen, @PathVariable("movieName") String movieName) {
        try {
            Map<String, String> response = service.set_hall_in_movie(screen, movieName);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/details/{movieName}")
    public ResponseEntity<?> getMovieDetails(@PathVariable("movieName") String movieName) {
        try {
            movie_pojo movie = service.get_movie_details(movieName);
            return ResponseEntity.ok(movie);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/book")
    public ResponseEntity<?> bookTickets(@RequestBody Map<String, Object> bookingData) {
        try {
            String movieName = (String) bookingData.get("movieName");
            int hallNumber = (int) bookingData.get("hallNumber");
            List<Integer> seatNumbers = (List<Integer>) bookingData.get("seatNumbers");
            user_DTO user = (user_DTO) bookingData.get("user");

            Map<String, Object> response = service.book_movie_hall(movieName, user, hallNumber, seatNumbers);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

//    @GetMapping("/book/{movieName}")
//    ResponseEntity<?> book_Ticket(@PathVariable("movieName") String movieName , @RequestBody user_DTO user){
//        try{
//            service.Search_Movie(movieName, user);
//            return new ResponseEntity<>(HttpStatus.OK);
//        } catch (Exception e) {
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        }
//    }


}
