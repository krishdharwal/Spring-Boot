package JobsService.pojo;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "Jobs_Table")
@Data
@NoArgsConstructor
public class job_pojo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long JobId;

    @Column(name = "CompanyId", nullable = false)
    private Long CompanyId;

    @Column(name = "LinkedId", nullable = false)
    public UUID LinkedId;

    @Column(name = "jobTitle", nullable = false)
    public String jobTitle;
    @Column(name = "posts" , nullable = false)
    private int posts;
    @Column(name = "location" , nullable = false)
    public String location;


}
