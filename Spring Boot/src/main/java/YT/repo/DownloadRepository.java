package YT.repo;
import YT.entity.Download;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.Optional;

public interface DownloadRepository extends MongoRepository<Download, String> {
    Optional<Download> findById(String id);
}
