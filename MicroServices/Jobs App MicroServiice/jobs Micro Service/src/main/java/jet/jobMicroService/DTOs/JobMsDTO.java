package jet.jobMicroService.DTOs;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;

@Data
@NoArgsConstructor
public class JobMsDTO {
    ObjectId id;
    public String jobTitle;
    public int posts;
    public String location;
}
