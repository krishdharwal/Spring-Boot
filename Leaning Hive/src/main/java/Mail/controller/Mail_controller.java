package Mail.controller;

import Mail.Mail_Query;
import Mail.pojo.Mail_Detail_pojo;
import Mail.pojo.Mail_pojo;
import Mail.services.Mail_Sender_services;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/mail")
@Slf4j
public class Mail_controller {

    @Autowired
    private Mail_Sender_services services;

    @Autowired
    private Mail_Query mailQuery;

    @PostMapping("/send")
    public String send_Mail(@RequestBody Mail_Detail_pojo message){
        services.SendMail(message.to,message.subject,message.message);
        return "[---Mail--Sended--to--> " + message.to + " ]";
    }

    @GetMapping
    public String health(){
        return "--ready--";
    }


    @GetMapping("/show-all")
    public ResponseEntity<?> showall(){
        List<Mail_pojo> body = services.findAll();
        return new ResponseEntity<>(body, HttpStatus.I_AM_A_TEAPOT);
    }

    @PostMapping("/add")
    public String Add(@RequestBody Mail_pojo body){
        try {
            services.save(body);
            return "---data--added---";
        }catch (Exception e){
            log.error("error hai jab mai mail pojo save ho rahi hai");
            return "--cant add--";
        }

    }

    @GetMapping("/sentiment")
    public String sendMailWIthSentiments(){
        try {
            services.send_mailTo_with_sentiments();
            return "--sended--";
        }catch (Exception e){
            log.error("errror in sending mail with sentiments");
            return "--error--";
        }
    }


}
