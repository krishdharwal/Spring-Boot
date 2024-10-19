package JobsService.pojo;


import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Document(collection = "Jobs_Table")
@Data
@NoArgsConstructor
public class job_pojo {
    @Id
    private ObjectId JobId;
    private Long CompanyId;
    public UUID LinkedId;
    public String jobTitle;
    private int posts;
    public String location;
}
