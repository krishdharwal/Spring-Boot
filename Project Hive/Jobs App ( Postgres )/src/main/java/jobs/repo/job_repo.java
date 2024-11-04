package jobs.repo;

import jobs.pojo.job_pojo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface job_repo extends JpaRepository<job_pojo, Long> {
}
