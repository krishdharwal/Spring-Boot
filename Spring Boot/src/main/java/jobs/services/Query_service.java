package jobs.services;

import jobs.pojo.job_pojo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.List;


@Slf4j
@Service
public class Query_service {

    @Autowired
    private MongoTemplate mongoTemplate;

    public job_pojo findByUserName(String name){
        try {
            Query query = new Query();
            query.addCriteria(Criteria.where(name).exists(true));
            List<job_pojo> userFromDb = mongoTemplate.find(query, job_pojo.class);
            return userFromDb.get(0);
        } catch (Exception e){
            log.error("-- error in findByUsername in Quer_service");
            return null;
        }
    }
}
