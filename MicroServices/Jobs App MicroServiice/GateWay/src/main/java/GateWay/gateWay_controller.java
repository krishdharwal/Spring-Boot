package GateWay;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class gateWay_controller {

    @GetMapping
    public String hello(){
        return "hello from GateWayApplication ";
    }
}
