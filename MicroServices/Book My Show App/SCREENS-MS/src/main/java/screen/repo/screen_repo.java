package screen.repo;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import screen.pojo.screen_pojo;


public interface screen_repo extends MongoRepository<screen_pojo, ObjectId> {

}
