package movies.clients;

import movies.Dto.updateSeat_DTO;
import org.bson.types.ObjectId;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "screenClient" , url = "${FeignClient.screen.url}")
public interface screen_client {

    @GetMapping("/book")
    List<Integer> Book_Seats(List<Boolean> seats);

    @PutMapping("/update-hall-seatsList")
    List<Boolean> updateSeats(@RequestBody updateSeat_DTO body);
}
