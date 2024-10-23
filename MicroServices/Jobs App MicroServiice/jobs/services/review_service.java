package jobs.services;

import jobs.pojo.job_pojo;
import jobs.pojo.reviews_pojo;
import jobs.repo.review_repo;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class review_service {

    @Autowired
    private review_repo repo;

    public String save(reviews_pojo body){
        if (body.getRating() <= 5 && body.getRating() > 0) {
            repo.save(body);
            return "--saved data";
        }
        return "-- give rating between 1 - 5 --";
    }

    public reviews_pojo findById(ObjectId id){
        try {
            reviews_pojo users = repo.findById(id).orElse(null);
            if (users  != null) {
                return users;
            }
        }catch (Exception e){
            log.error("-- error in findbyid in  JOB services --");
            return null;
        }
        return null;
    }

    // delete
    public void delete(ObjectId id){
        try {
            repo.deleteById(id);
        }catch (Exception e){
            log.error("-- error in delete in review serices --");
        }
    }
}
