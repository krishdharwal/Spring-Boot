package Weather.repo;

import Weather.pojo.API_pojo;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface repoWeather extends MongoRepository<API_pojo, ObjectId> {
}
