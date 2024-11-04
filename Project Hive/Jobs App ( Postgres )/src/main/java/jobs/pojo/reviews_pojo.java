package jobs.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.persistence.Id;

@Entity
@Table(name = "reviews_Table")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class reviews_pojo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "reviews")
    private String review;
    @Column(name = "ratings")
    private int rating;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private company_pojo companyPojo;
    }
