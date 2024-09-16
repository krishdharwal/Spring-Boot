package Mail;

import learning.Services.Mail_Sender_services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/mail")
public class Mail_controller {

    @Autowired
    private Mail_Sender_services services;

//    @Autowired
//    private Mail_Query mailQuery;

    @GetMapping("/send")
    public String send_Mail(@RequestBody Mail_Detail_pojo message){
        services.SendMail(message.to,message.subject,message.message);
        return "[---Mail--Sended--to--> " + message.to + " ]";
    }

    @GetMapping
    public String health(){
        return "--ready--";
    }


//    @GetMapping("/show-all")
//    public ResponseEntity<?> showall(){
//        List<Mail_pojo> body = services.findAll();
//        return new ResponseEntity<>(body,HttpStatus.I_AM_A_TEAPOT);
//    }

//    @PostMapping
//    public String Add(@RequestBody Mail_pojo body){
//        services.save(body);
//        return "---data--added---";
//    }

//    @GetMapping("/find-with-mail")
//    public List<Mail_pojo> findWithMail(){
//      List<Mail_pojo> body =  mailQuery.HaveMailPeoples();
//          return body;
//    }

}
