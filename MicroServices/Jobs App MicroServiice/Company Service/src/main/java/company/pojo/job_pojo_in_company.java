//package company.pojo;
//
//import jakarta.persistence.*;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//import java.util.UUID;
//
//@Entity
//@Data
//@NoArgsConstructor
//public class job_pojo_in_company {
//@Id
//    private Long JobId;
//    private Long CompanyId;
//    public UUID LinkedId;
//    public String jobTitle;
//    private int posts;
//    public String location;
//
//    @ManyToOne
//    @JoinColumn(name = "Jobcompanyid")
//    private company_pojo companyPojo;
//
//
//}
