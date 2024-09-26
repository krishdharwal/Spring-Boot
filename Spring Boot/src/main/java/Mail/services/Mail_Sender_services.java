package Mail.services;

import Mail.Mail_Query;
import Mail.pojo.Mail_pojo;
import Mail.repo.Mail_repo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class Mail_Sender_services {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private Mail_repo mailRepo;

    @Autowired
    private Mail_Query query;

    public void SendMail(String to,String subject,String body){
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(to);
            message.setSubject(subject);
            message.setText(body);

            javaMailSender.send(message);

        }catch (Exception e){
            System.out.println("errrpr" + e);
            log.error("-----Error while sending mail-----", e);
        }
    }

    public List<Mail_pojo> findAll() {
        return mailRepo.findAll();
    }

    public void save(Mail_pojo body) {
        mailRepo.save(body);
    }

    public void send_mailTo_with_sentiments(){
        try {
            List<Mail_pojo> users = query.Find_Mail_with_sentiments();

            if (users != null) {
                int i = 0;
                while (i < users.size()){
                    Mail_pojo user = users.get(i);
                    String sentiment = user.getSentiments().toString();
                    String mailAddress = user.getMailAdress();

                    SimpleMailMessage message  = new SimpleMailMessage();
                    message.setTo(mailAddress);
                    message.setSubject("Sentiments");
                    message.setText(sentiment);


                    javaMailSender.send(message);

                    i++;
                }


            }
        }catch (Exception e){
            log.error("error in send_mailTo_with_sentiments");
        }
    }
}
