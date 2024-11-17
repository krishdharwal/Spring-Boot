package user.Clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import user.pojo.movie_reserve_pojo;
import user.pojo.user_pojo;

@FeignClient(name = "movieClient" , url = "${FeignClient.movie.url}")
public interface movie_client {

    @GetMapping("book_reserved")
    void Book_seats_that_are_reserved(movie_reserve_pojo movieReservePojo, user_pojo userPojo);

}
