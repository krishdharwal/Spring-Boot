package org.CompanyMicroService.DTOs;//package jet.jobMicroService.DTOs;
import ReviewsMS.pojo.reviews_pojo;
import jet.jobMicroService.pojojob.jobMS_pojo;
import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.index.Indexed;

import java.util.ArrayList;
import java.util.List;

public class CompanyMsDTO {
     ObjectId id;
     String companyName;
     String type;
     Double averageRating;
     List<jobMS_pojo> jobsList = new ArrayList<>();
     List<reviews_pojo> reviewList = new ArrayList<>();
}
