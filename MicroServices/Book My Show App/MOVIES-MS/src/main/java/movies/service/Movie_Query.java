package movies.service;

import movies.pojo.movie_pojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;



@Service
public class Movie_Query {

    @Autowired
    private MongoTemplate mongoTemplate;

    public movie_pojo find_Movie_By_Name(String Movie_name){
        Query query = new Query();
        query.addCriteria(Criteria.where("name").is(Movie_name));
        return mongoTemplate.find(query,movie_pojo.class).get(0);
    }

}
