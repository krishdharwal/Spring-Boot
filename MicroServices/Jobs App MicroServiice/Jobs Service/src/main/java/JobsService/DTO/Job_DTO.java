package JobsService.DTO;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
public class Job_DTO {
 public Long JobId;
 public Long CompanyId;
 public UUID LinkedId;
 public String jobTitle;
 public int posts;
 public String location;

}
