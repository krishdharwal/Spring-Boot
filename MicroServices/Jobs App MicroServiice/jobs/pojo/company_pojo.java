package jobs.pojo;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "company DB")
@Data
@NoArgsConstructor
public class company_pojo {
    @Id
    private ObjectId id;
    private String companyName;
    private String type;

    @DBRef
    private List<job_pojo> jobsList = new ArrayList<>();
    @DBRef
    private List<reviews_pojo> reviewList = new ArrayList<>();

}
