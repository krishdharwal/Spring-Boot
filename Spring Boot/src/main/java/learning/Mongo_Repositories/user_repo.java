package learning.Mongo_Repositories;

import learning.POJO.user;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface user_repo extends MongoRepository<user, ObjectId> {
    // this will act as a query and spring will automatically make this method
    public user findByUsername(String name);
}
