package jobs.pojo;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "jobs_data")
@Data
@NoArgsConstructor
public class job_pojo {
    @Id
    ObjectId id;
    @NonNull
    public String jobTitle;
    private int posts;
    public String location;
}
