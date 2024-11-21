package user.Clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import user.Dto.movie_reserve_dto;
import user.pojo.movie_reserve_pojo;

@FeignClient(name = "movieClient" , url = "http://localhost:9091/movie")
public interface movie_client {

    @PutMapping("/book-reserved")
     void Book_seats_that_are_reserved(@RequestBody movie_reserve_pojo movieReservePojo);
//solve this
//    , @RequestBody user_DTO userPojo
    @DeleteMapping("/cancel-booked/{id}")
     void Delete_Booked_ticket(@RequestBody movie_reserve_dto movieReserveDto);


    }
