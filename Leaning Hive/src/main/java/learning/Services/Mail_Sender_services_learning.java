package learning.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
//@Slf4j
public class Mail_Sender_services_learning {

    @Autowired
    private JavaMailSender javaMailSender;

//    @Autowired
//    private Mail_repo mailRepo;

    public void SendMail(String to,String subject,String body){
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(to);
            message.setSubject(subject);
            message.setText(body);

            javaMailSender.send(message);

        }catch (Exception e){
            System.out.println("errrpr" + e);
//            log.error("-----Error while sending mail-----", e);
        }
    }

//    public List<Mail_pojo> findAll() {
//        return mailRepo.findAll();
//    }
//
//    public void save(Mail_pojo body) {
//        mailRepo.save(body);
//    }
}
