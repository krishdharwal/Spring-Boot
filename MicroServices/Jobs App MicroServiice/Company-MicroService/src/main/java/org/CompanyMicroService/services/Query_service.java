package org.CompanyMicroService.services;
import org.CompanyMicroService.pojo.companyMS_pojo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import jet.jobMicroService.pojojob.jobMS_pojo;

import java.util.List;


@Slf4j
@Service
public class Query_service {

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
            log.error("-- error in findByJobName in Quer_service");
            return null;
        }
    }



    // company queries
    public List<companyMS_pojo> findByCompanyName(String name, String type){
        Query query = new Query();
        query.addCriteria(Criteria.where("companyName").is(name));
        query.addCriteria(Criteria.where("type").is(type));
        List<companyMS_pojo> company = mongoTemplate.find(query, companyMS_pojo.class);
        return company;
    }

    public companyMS_pojo findByCompanyName(String name){
        Query query = new Query();
        query.addCriteria(Criteria.where("companyName").is(name));
        List<companyMS_pojo> company = mongoTemplate.find(query, companyMS_pojo.class);
        return company.get(0);
    }


    public List<companyMS_pojo> ShowAllCompanyOfType(String type){
        Query query = new Query();
        query.addCriteria(Criteria.where("type").is(type));
        return mongoTemplate.find(query, companyMS_pojo.class);
    }

    // review queries
}
