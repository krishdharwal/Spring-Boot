package learning.Controllers;

import learning.POJO.user_info;
import learning.Services.user_info_service;
import learning.Services.user_service;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import learning.POJO.user;

import java.util.List;

@RestController
@RequestMapping("/info")
public class user_info_controller {

    @Autowired
    private user_info_service service_info;

    @Autowired
    private user_service service_user;

    @GetMapping
    public ResponseEntity<?> get_info(){
        try{
        Authentication auth =  SecurityContextHolder.getContext().getAuthentication();
        String userName = auth.getName();
        user User = service_user.findByname(userName);
        List<user_info> info = User.getInfo();
        return new ResponseEntity<>(info,HttpStatus.ACCEPTED);
    }catch (Exception e){
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    }

    @PostMapping
    @Transactional
    public ResponseEntity<?> save(@RequestBody user_info body){
        try {
            Authentication auth =  SecurityContextHolder.getContext().getAuthentication();
            String userName = auth.getName();

            service_info.save(body,userName);
            return new ResponseEntity<>("--data--added--", HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("{id}")
    @Transactional
    public ResponseEntity<?> update(@RequestBody user_info new_info,@PathVariable ObjectId id){
        try {
            Authentication auth =  SecurityContextHolder.getContext().getAuthentication();
            String userName = auth.getName();
            user User = service_user.findByname(userName);

            boolean present = User.getInfo().stream().anyMatch(x -> x.getId().equals(id));
            if (present){
                service_info.update(new_info,id);
                return new ResponseEntity<>("--updated--" , HttpStatus.ACCEPTED);
            }

            return new ResponseEntity<>("--error occur--" , HttpStatus.BAD_REQUEST);
    }catch (Exception e){
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    }

    @DeleteMapping("{id}")
    @Transactional
    public ResponseEntity<?> delete(@PathVariable ObjectId id){
        try {
            Authentication auth =  SecurityContextHolder.getContext().getAuthentication();
            String userName = auth.getName();
            service_info.deleteById(userName,id);
            return new ResponseEntity<>("--deleted--" , HttpStatus.ACCEPTED);
        }catch (Exception e){
        return new ResponseEntity<>(HttpStatus.I_AM_A_TEAPOT);
    }
    }
}