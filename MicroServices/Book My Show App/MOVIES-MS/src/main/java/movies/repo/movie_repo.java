package movies.repo;

import movies.pojo.movie_pojo;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface movie_repo extends MongoRepository<movie_pojo, ObjectId> {

}
