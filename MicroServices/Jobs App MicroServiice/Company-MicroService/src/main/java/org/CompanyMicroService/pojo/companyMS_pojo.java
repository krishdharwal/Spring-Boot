package org.CompanyMicroService.pojo;

import jet.jobMicroService.pojojob.jobMS_pojo;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "companyMS")
@Data
@NoArgsConstructor
public class companyMS_pojo {
    @Id
    private ObjectId id;
    private String companyName;
    private String type;
    private List<jobMS_pojo> jobsList = new ArrayList<>();
}
