package learning.Mongo_Repositories;


import learning.POJO.user;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Query_s_criteria {

    @Autowired
    private MongoTemplate mongoTemplate;

    public List<user> findByUsername(String name){
        Query query = new Query();
        query.addCriteria(Criteria.where("username").in(name));
//        query.addCriteria(Criteria.where("roles").is("ADMIN"));
        List<user> body  = mongoTemplate.find(query, user.class);
        return body;
    }

}
