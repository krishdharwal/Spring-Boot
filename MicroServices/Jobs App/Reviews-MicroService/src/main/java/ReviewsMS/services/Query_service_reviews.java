package ReviewsMS.services;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class Query_service_reviews {

    @Autowired
    private MongoTemplate mongoTemplate;


}
