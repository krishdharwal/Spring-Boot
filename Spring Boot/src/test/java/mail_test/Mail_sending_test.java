package mail_test;

import Mail.services.Mail_Sender_services;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class Mail_sending_test {

    @Autowired
    Mail_Sender_services services;

    @Test
    void send_sentiments(){
        services.send_mailTo_with_sentiments();
    }

}
