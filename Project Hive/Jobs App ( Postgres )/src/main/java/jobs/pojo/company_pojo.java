package jobs.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Id;


@Entity
@Table(name = "Company_Table")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class company_pojo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "company" , nullable = false)
    private String company;

    @Column(name = "Type", nullable = false)
    private String type;

    @OneToMany(mappedBy = "companyPojo", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<job_pojo> jobsList = new ArrayList<>();

//    @OneToMany(mappedBy = "companyPojo", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//    private List<reviews_pojo> reviewList = new ArrayList<>();

}
