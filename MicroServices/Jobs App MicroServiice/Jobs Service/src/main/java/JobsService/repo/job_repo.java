package JobsService.repo;

import JobsService.pojo.job_pojo;
import jdk.dynalink.linker.LinkerServices;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface job_repo extends JpaRepository<job_pojo, Long> {
}
