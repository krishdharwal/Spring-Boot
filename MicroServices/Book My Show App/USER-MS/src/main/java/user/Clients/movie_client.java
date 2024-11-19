package user.Clients;

import user.Dto.movie_reserve_dto;
import org.springframework.web.bind.annotation.DeleteMapping;
import user.Dto.user_DTO;
import user.pojo.movie_reserve_pojo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "movieClient" , url = "${FeignClient.movie.url}")
public interface movie_client {

    @PutMapping("/book-reserved")
     void Book_seats_that_are_reserved(@RequestBody movie_reserve_pojo movieReservePojo, @RequestBody user_DTO userPojo);

    @DeleteMapping("/cancel-booked/{id}")
     void Delete_Booked_ticket(@RequestBody movie_reserve_dto movieReserveDto);


    }
