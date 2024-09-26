package Mail.repo;

import Mail.pojo.Mail_pojo;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface Mail_repo extends MongoRepository<Mail_pojo, ObjectId> {
}
