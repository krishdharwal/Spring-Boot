package JobsService.DTO;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Job_DTO {
 public Long JobId;
 public Long CompanyId;
 private Long LinkedId;
 public String jobTitle;
 public int posts;
 public String location;

}
