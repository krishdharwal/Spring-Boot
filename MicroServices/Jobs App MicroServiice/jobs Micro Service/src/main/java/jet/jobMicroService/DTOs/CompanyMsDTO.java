package jet.jobMicroService.DTOs;

import jet.jobMicroService.pojojob.jobMS_pojo;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

public class CompanyMsDTO {
    public ObjectId id;
    public String companyName;
    public String type;
    public List<jobMS_pojo> jobsList = new ArrayList<>();
}
