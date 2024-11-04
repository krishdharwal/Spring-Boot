package jobs.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;




import javax.persistence.*;
import javax.persistence.Id;

@Entity
@Table(name = "Jobs_Table_postgres")
@Data
@AllArgsConstructor
public class job_pojo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "jobTitle", nullable = false)
    public String jobTitle;
    @Column(name = "posts" , nullable = false)
    private int posts;
    @Column(name = "location" , nullable = false)
    public String location;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private company_pojo companyPojo;


    public job_pojo(){}
}
