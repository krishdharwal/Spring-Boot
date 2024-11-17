package movies.clients;

import movies.Dto.user_DTO;
import org.bson.types.ObjectId;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "userClient" , url = "${FeignClient.user.url}")
public interface user_client {
    @GetMapping("/find/{name}")
    user_DTO findByName(@PathVariable String name);

    @PutMapping("/update_reserved_seats")
    ObjectId update_reserved_seats(@RequestBody user_DTO user);

    @PutMapping("/update-user")
    void update_User_Movies(@RequestBody user_DTO user);

}
