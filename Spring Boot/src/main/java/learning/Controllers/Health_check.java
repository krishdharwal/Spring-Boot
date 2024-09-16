package learning.Controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/health")
public class Health_check {

    @GetMapping
    public String health(){
        return "--- health is ok :) ---" ;
    }
}
