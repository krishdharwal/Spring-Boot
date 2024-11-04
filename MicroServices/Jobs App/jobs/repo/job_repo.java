package jobs.repo;

import jobs.pojo.job_pojo;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface job_repo extends MongoRepository<job_pojo, ObjectId> {
}
