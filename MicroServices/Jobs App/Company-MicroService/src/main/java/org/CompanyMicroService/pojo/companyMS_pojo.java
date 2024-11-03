package org.CompanyMicroService.pojo;

import ReviewsMS.pojo.reviews_pojo;
import jet.jobMicroService.pojojob.jobMS_pojo;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "companyMS")
@Data
@NoArgsConstructor
public class companyMS_pojo {
    @Id
    private ObjectId id;
    @Indexed(unique = true)
    @NonNull
    private String companyName;
    private String type;
    private Double averageRating;
    private List<jobMS_pojo> jobsList = new ArrayList<>();
    private List<reviews_pojo> reviewList = new ArrayList<>();
}
