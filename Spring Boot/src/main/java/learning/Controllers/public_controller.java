package learning.Controllers;

import learning.POJO.user;
import learning.Services.user_info_service;
import learning.Services.user_service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
public class public_controller {

    @Autowired
    private user_service service_user;

    @Autowired
    private user_info_service service_info;


    @PostMapping("/user")
    public String save(@RequestBody user body){
        service_user.save(body);
        return "--data -- added--";
    }

}
