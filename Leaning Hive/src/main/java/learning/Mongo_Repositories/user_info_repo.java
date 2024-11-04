package learning.Mongo_Repositories;

import learning.POJO.user_info;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface user_info_repo extends MongoRepository<user_info, ObjectId> {
}
