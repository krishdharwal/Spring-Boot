package screen.controller;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import screen.service.screen_service;

import java.util.List;


@RestController("/screen")
@Slf4j
public class screen_controller {

    @Autowired
    private screen_service service;

    @GetMapping("/book")
    public List<Integer> Book_Seats(List<Boolean> seats){
        try{
           return service.Book_Seats(seats);
        }catch (Exception e){
            log.error(" -- error in Book seatsList in screen_controller -- ");
            return null;
        }
    }

    @PutMapping("/update-hall-seatsList")
    public List<Boolean> updateSeats(ObjectId hall_id, List<Integer> booked_seets){
        try{
           return service.update_Reserved_seets_of_hall(hall_id,booked_seets);
        } catch (Exception e) {
            log.error(" -- error in update seatsList in screen_controller --  ");
            return null;
        }
    }


}
