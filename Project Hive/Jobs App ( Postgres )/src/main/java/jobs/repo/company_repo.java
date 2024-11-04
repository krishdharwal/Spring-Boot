package jobs.repo;

import jobs.pojo.company_pojo;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface company_repo extends JpaRepository<company_pojo, Long> {
    company_pojo findByCompany(String companyName);

    List<company_pojo> findByType(String type);

    List<company_pojo> findAll(Specification<company_pojo> specification);
}
