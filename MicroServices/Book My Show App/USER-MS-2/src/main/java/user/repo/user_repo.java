package user.repo;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import user.pojo.user_pojo;

public interface user_repo extends MongoRepository<user_pojo , ObjectId> {

    user_pojo findByName(String username);
}
