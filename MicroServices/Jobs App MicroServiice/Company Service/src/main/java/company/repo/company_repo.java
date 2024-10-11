package company.repo;

import company.pojo.company_pojo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface company_repo extends JpaRepository<company_pojo, Long> {
     company_pojo findByCompanyName(String companyName);

    List<company_pojo> findByType(String type);

}
