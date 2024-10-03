import jobs.services.Query_service;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;


@SpringBootTest
@Configuration
public class company_test {

    @Autowired
    private Query_service service;

    @Test
    public void findCompanyByName(){
        service.findByCompanyName("google","tech");

    }
}
