package screen.controller;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import screen.Dto.bookSeat_bool_list__DTO;
import screen.Dto.screen_DTO;
import screen.Enum.Ticket_Status;
import screen.service.screen_service;

import java.util.List;


@RestController
@RequestMapping("/screen")
@Slf4j
public class screen_controller {

    @Autowired
    private screen_service service;

    @GetMapping("/book")
    public List<Integer> Book_Seats(@RequestBody bookSeat_bool_list__DTO list__dto){
        try{
           return service.Book_Seats(list__dto.getSeats());
        }catch (Exception e){
            log.error(" -- error in Book seatsList in screen_controller -- ");
            return null;
        }
    }

    @PutMapping("/update-hall-seatsList")
    public List<Ticket_Status> updateSeats(ObjectId hall_id, List<Integer> booked_seets){
        try{
           return service.update_Reserved_seets_of_hall(hall_id,booked_seets);
        } catch (Exception e) {
            log.error(" -- error in update seatsList in screen_controller --  ");
            return null;
        }
    }

    @PostMapping("/save")
    public void Save_Hall(@RequestBody screen_DTO screenDto){
        try{
            service.save(screenDto);
        }catch (Exception e){
            log.error(" -- error in save in screen_controller -- ");
        }
    }


}
