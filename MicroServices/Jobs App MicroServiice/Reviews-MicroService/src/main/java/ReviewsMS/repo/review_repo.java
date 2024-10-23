package ReviewsMS.repo;


import ReviewsMS.pojo.reviews_pojo;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface review_repo extends MongoRepository<reviews_pojo, ObjectId> {

}
