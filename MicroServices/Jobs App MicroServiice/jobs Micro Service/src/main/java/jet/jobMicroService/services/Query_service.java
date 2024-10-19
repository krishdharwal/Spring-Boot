package jet.jobMicroService.services;
import jet.jobMicroService.pojojob.jobMS_pojo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;


//@Slf4j
@Service
public class Query_service {

    private static final Logger log = LoggerFactory.getLogger(Query_service.class);

    @Autowired
    private MongoTemplate mongoTemplate;


    // job queries
    public jobMS_pojo findByJobName(String name){
        try {
            Query query = new Query();
            query.addCriteria(Criteria.where("jobTitle").is(name));
            List<jobMS_pojo> userFromDb = mongoTemplate.find(query, jobMS_pojo.class);
            return userFromDb.get(0);
        } catch (Exception e){
            log.error("-- error in findByJobName in Query_service");
            return null;
        }
    }



}
