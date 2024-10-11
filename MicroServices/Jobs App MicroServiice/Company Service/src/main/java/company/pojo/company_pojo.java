package company.pojo;

import JobsService.pojo.job_pojo;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.ArrayList;
import java.util.List;



@Entity
@Table(name = "Company_Table")
@Data
@NoArgsConstructor
public class company_pojo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "Companies" , nullable = false)
    private String companyName;

    @Column(name = "Type", nullable = false)
    private String type;

//    @OneToMany(mappedBy = "companyPojo", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Long> jobsList = new ArrayList<>();

    public void addJobId(Long jobId) {
        jobsList.add(jobId);
    }
}