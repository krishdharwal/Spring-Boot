package movies.service;

import lombok.extern.slf4j.Slf4j;
import movies.pojo.movie_pojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class Movie_Query {

    @Autowired
    private MongoTemplate mongoTemplate;

    public movie_pojo find_Movie_By_Name(String Movie_name){
        try {
            Query query = new Query();
            query.addCriteria(Criteria.where("name").is(Movie_name));
            movie_pojo moviePojo =  mongoTemplate.find(query, movie_pojo.class).get(0);
            assert moviePojo != null;
            return moviePojo;
        } catch (Exception e) {
            log.error("  error| in find by movie ");
            return null;
        }
    }

}
