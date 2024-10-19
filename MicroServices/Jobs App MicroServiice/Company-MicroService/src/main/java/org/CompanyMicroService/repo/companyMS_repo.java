package org.CompanyMicroService.repo;

import org.CompanyMicroService.pojo.companyMS_pojo;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface companyMS_repo extends MongoRepository<companyMS_pojo, ObjectId> {
}
