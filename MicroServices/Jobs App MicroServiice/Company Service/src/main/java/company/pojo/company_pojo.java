package company.pojo;

import JobsService.pojo.job_pojo;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


import java.util.ArrayList;
import java.util.List;


@Document(collection = "Company MS")
@Data
@NoArgsConstructor
public class company_pojo {
    @Id
    private ObjectId id;
    @NonNull
    private String companyName;
    @NonNull
    private String type;
    private List<job_pojo> JobsList = new ArrayList<>();

}
