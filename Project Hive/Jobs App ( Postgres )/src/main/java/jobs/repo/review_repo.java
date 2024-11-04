package jobs.repo;

import jobs.pojo.reviews_pojo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface review_repo extends JpaRepository<reviews_pojo, Long> {

}
