package user.controller;

import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import user.Dto.user_DTO;
import user.service.User_Queries;
import user.service.user_service;

@RestController
@RequestMapping("/user_client")
@Slf4j
public class user_client_Controller {

    @Autowired
    private user_service service;

    @Autowired
    private User_Queries userQueries;

    @GetMapping("/find/{name}")
    public user_DTO findByName(@PathVariable String name){
        try {
            return service.findByName(name);
        } catch (Exception e) {
            log.error(" -- error in findByName  in  client_Controller -- ");
            return null;
        }
    }

    @PutMapping("/update_reserved_seats")
    public ObjectId update_reserved_seats(@RequestBody user_DTO user){
        try {
            return service.update_User_Reserved_seats(user);
        }catch (Exception e){
            log.error(" -- error in update_reserved_seats in client_Controller ");
            return null;
        }
    }

    @PutMapping("/update-user")
    void update_User_Movies(@RequestBody user_DTO user){
        try {
            service.update_User_Movies(user);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
