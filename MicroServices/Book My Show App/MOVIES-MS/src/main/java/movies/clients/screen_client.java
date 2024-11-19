package movies.clients;

import movies.Dto.bookSeat_bool_list__DTO;
import movies.Dto.updateSeat_DTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import movies.Dto.screen_DTO;

import java.util.List;

@FeignClient(name = "screenClient" , url = "${FeignClient.screen.url}")
public interface screen_client {

    @GetMapping("/book")
    List<Integer> Book_Seats(@RequestBody bookSeat_bool_list__DTO list__dto);

    @PutMapping("/update_reserved_seats-hall-seatsList")
    List<Boolean> updateSeats(@RequestBody updateSeat_DTO body);

    @PostMapping("/save")
    void Save_Hall(@RequestBody screen_DTO screenDto);
}
