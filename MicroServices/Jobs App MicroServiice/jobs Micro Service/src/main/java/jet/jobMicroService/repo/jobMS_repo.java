package jet.jobMicroService.repo;

import jet.jobMicroService.pojojob.jobMS_pojo;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface jobMS_repo extends MongoRepository<jobMS_pojo, ObjectId> {
}
