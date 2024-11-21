package movies.repo;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import movies.pojo.user_pojo;

public interface user_repo extends MongoRepository<user_pojo , ObjectId> {
}