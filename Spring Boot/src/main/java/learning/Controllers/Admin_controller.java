package learning.Controllers;

import learning.Mongo_Repositories.Query_s_criteria;
import learning.POJO.user;
import learning.Services.user_info_service;
import learning.Services.user_service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/admin")
@Slf4j
public class Admin_controller {
    @Autowired
    private user_service service_user;

    @Autowired
    private user_info_service service_info;

    @Autowired
    private Query_s_criteria queryService;

    @GetMapping("/find/{name}")
    public List<user> findByName(@PathVariable String name){
        try {
            return queryService.findByUsername(name);
        }catch (Exception e){
            log.error("body mai kuch error a gaya");
            return null;
        }
    }

    @GetMapping
    public String Admin(){
        return "--Admin--Logined--";
    }

    @GetMapping("/show-infos")
    public ResponseEntity<?> showAll_User_info(){
        try {
            return new ResponseEntity<>(service_info.showall(), HttpStatus.ACCEPTED);
        }catch (Exception e){
            return new ResponseEntity<>( HttpStatus.BAD_REQUEST);
        }
    }


    @GetMapping("/show-users")
    public ResponseEntity<?> showAll(){
        List<user> users = service_user.findAll();
        if (users != null) {
            return new ResponseEntity<>(users, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_GATEWAY);
    }

    @PostMapping("/add-admin")
    public ResponseEntity<?> Create_admin(@RequestBody user body){
        if (body != null){
            service_user.save_Admin(body);
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

}
