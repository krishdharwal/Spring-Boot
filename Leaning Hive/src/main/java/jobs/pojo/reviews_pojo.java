package jobs.pojo;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document (collection = "Review pojo")
@Data
public class reviews_pojo {
    @Id
    private ObjectId id;
    private String review;
    private int rating;
    }
