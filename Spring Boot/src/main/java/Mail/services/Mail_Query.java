package Mail;

import Mail.pojo.Mail_pojo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class Mail_Query {

    @Autowired
    private MongoTemplate mongoTemplate;

    public List<Mail_pojo> HaveMailPeoples(){
        Query query = new Query();
        query.addCriteria(Criteria.where("mailAdress").exists(true));
        List<Mail_pojo> users_with_mail = mongoTemplate.find(query,Mail_pojo.class);
        return users_with_mail;
    }

    public List<Mail_pojo> Find_Mail_with_sentiments(){
        try {
            Query query = new Query();
            query.addCriteria(Criteria.where("sentiments").exists(true));
            List<Mail_pojo> users = mongoTemplate.find(query, Mail_pojo.class);
            return users;
        }catch (Exception e){
            log.error("error in sendMail_with_sentiments"  + HttpStatus.BAD_REQUEST);
            return null;
        }

    }
}
