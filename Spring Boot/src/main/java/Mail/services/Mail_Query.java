//package Mail;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.mongodb.core.MongoTemplate;
//import org.springframework.data.mongodb.core.query.Criteria;
//import org.springframework.data.mongodb.core.query.Query;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//@Slf4j
//public class Mail_Query {
//
//    @Autowired
//    private MongoTemplate mongoTemplate;
//
//    public List<Mail_pojo> HaveMailPeoples(){
//        Query query = new Query();
//        query.addCriteria(Criteria.where("mailAdress").exists(true));
//        List<Mail_pojo> users_with_mail = mongoTemplate.find(query,Mail_pojo.class);
//        return users_with_mail;
//    }
//
//
//}
