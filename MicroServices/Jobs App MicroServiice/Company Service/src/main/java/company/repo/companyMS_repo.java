package company.repo;

import company.pojo.company_pojo;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface companyMS_repo extends MongoRepository<company_pojo, ObjectId> {

    List<company_pojo> findByType(String type);
}
