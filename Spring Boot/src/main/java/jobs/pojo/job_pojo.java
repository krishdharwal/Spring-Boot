package jobs.pojo;

import lombok.Data;
import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "jobs_data")
@Data
public class job_pojo {
    @Id
    ObjectId id;
    @NonNull
    String username;
    @NonNull
    String job;
    String location;
}
