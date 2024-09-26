package learning.Controllers;

import learning.POJO.user;
import learning.Services.UserDetailsServiceImpl;
import learning.Services.user_info_service;
import learning.Services.user_service;
import learning.utilis.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
@Slf4j
public class public_controller {
    @Autowired
    private UserDetailsServiceImpl userDetailsServiceimpl;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private user_service service_user;

    @Autowired
    private user_info_service service_info;


    @PostMapping("/signup")
    public String save(@RequestBody user body){
        service_user.save(body);
        return "--data -- added--";
    }

    @PostMapping("/getToken")
    public ResponseEntity<String> token(@RequestBody user body){
        try{
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(body.getUsername(),body.getPassword()));
            UserDetails User = userDetailsServiceimpl.loadUserByUsername(body.getUsername());
                String token = jwtUtil.generateToken(User.getUsername());
                return new ResponseEntity<>(token, HttpStatus.ACCEPTED);
        }catch (Exception e){
            log.error("error while making token");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}
