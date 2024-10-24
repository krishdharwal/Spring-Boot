package org.CompanyMicroService.DTOs;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;

@Data
@NoArgsConstructor
public class JobMsDTO {
    ObjectId id;
    String jobTitle;
    int posts;
    String location;
    String companyName;
}
