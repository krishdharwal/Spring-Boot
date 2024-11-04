package YT.repo;
import YT.entity.linkPojo;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DownloadRepository extends MongoRepository<linkPojo, String> {
}
