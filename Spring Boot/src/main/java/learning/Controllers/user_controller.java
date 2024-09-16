package learning.Controllers;

import learning.POJO.user;
import learning.Services.UserDetailsServiceImpl;
import learning.Services.user_service;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/user")
public class user_controller {

    @Autowired
    private user_service service;

    @Autowired
    private UserDetailsServiceImpl impl;

    @GetMapping
    public String Logined(){
        return "--- Logined Sucessfully :) ---" ;
    }

    @GetMapping("/detail")
    public UserDetails findbyname() {
        Authentication auth =  SecurityContextHolder.getContext().getAuthentication();
        String userName = auth.getName();
        return impl.loadUserByUsername(userName);
    }

    @PutMapping
    @Transactional
    public String update(@RequestBody user body){
        Authentication auth =  SecurityContextHolder.getContext().getAuthentication();
        String userName = auth.getName();
        user Current_user = service.findByname(userName);

        Current_user.setUsername(body.getUsername());
        Current_user.setPassword(body.getPassword());
        service.save(Current_user);
        return "--updated--";

    }

    @DeleteMapping
    @Transactional
    public String delete(){
        Authentication auth =  SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName();
        user body = service.findByname(name);
        ObjectId id  = body.getId();
        service.deleteById(id);
        return "--deleted--";
            }

}
