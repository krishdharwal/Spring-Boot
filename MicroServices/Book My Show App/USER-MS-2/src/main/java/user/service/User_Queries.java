package user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import user.pojo.user_pojo;

import java.util.List;


@Service
public class User_Queries {

    @Autowired
    private MongoTemplate mongoTemplate;


    public user_pojo findByName(String Gname){

        Query query = new Query();
        query.addCriteria(Criteria.where("name").is(Gname));
         user_pojo user = mongoTemplate.find(query, user_pojo.class).get(0);
         assert user != null;
         return user;

    }


}
