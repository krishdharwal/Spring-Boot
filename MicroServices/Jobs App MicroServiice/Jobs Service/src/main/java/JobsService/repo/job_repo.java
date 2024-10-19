package JobsService.repo;

import JobsService.pojo.job_pojo;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface job_repo extends MongoRepository<job_pojo, ObjectId> {
}
