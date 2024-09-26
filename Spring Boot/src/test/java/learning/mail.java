package learning;

import learning.Services.Mail_Sender_services_learning;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class mail {

    @Autowired
    Mail_Sender_services_learning services;

    @Test
    void send(){
        services.SendMail("knightkrishcoc3@gmail.com","test,","ogurwjfeginr");
    }

}
