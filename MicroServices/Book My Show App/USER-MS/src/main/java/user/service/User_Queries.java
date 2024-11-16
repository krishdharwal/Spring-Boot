package user.service;


import org.bouncycastle.jcajce.spec.RawEncodedKeySpec;
import org.bson.assertions.Assertions;
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


    public List<user_pojo> findByName(String name){

        Query query = new Query();
        query.addCriteria(Criteria.where("name").is(name));
        return Assertions.assertNotNull(mongoTemplate.find(query, user_pojo.class));

    }
}
