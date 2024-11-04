package jobs.repo;

import jobs.pojo.company_pojo;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface company_repo extends MongoRepository<company_pojo, ObjectId> {
}
