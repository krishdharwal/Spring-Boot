package movies.repo;

import movies.pojo.movie_reserve_pojo;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface schedule_repo extends MongoRepository<movie_reserve_pojo , ObjectId> {
}
