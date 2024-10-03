package jobs.services;
// dfsanosdaifnvoisdanvogifsdvgfsgvsfdvggfssfd
import jobs.pojo.company_pojo;
import jobs.pojo.job_pojo;
import jobs.pojo.reviews_pojo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;


@Slf4j
@Service
public class Query_service {

    @Autowired
    private MongoTemplate mongoTemplate;


    // job queries

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



    // company queries

    public List<company_pojo> findByCompanyName(String name,String type){
        Query query = new Query();
        query.addCriteria(Criteria.where("companyName").is(name));
        query.addCriteria(Criteria.where("type").is(type));
        List<company_pojo> company = mongoTemplate.find(query,company_pojo.class);
        return company;
    }

    public company_pojo findByCompanyName(String name){
        Query query = new Query();
        query.addCriteria(Criteria.where("companyName").is(name));
        List<company_pojo> company = mongoTemplate.find(query,company_pojo.class);
        return company.get(0);
    }


    public List<company_pojo> ShowAllCompanyOfType(String type){
        Query query = new Query();
        query.addCriteria(Criteria.where("type").is(type));
        return mongoTemplate.find(query,company_pojo.class);
    }

    // review queries
    public List<reviews_pojo> ReviewOfCompany(String name){
        Query query = new Query();
         query.addCriteria(Criteria.where("companyName").is(name));
        List<company_pojo> company = mongoTemplate.find(query,company_pojo.class);
        return company.get(0).getReviewList();
    }
}
