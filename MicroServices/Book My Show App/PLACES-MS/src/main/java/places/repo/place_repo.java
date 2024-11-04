package places.repo;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import places.pojo.place_pojo;

public interface place_repo extends MongoRepository<place_pojo, ObjectId> {

}
