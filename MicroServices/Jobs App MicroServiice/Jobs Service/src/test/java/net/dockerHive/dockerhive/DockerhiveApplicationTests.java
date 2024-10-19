package net.dockerHive.dockerhive;

import JobsService.services.job_service;
import JobsService.start_job;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = start_job.class)
class DockerhiveApplicationTests {

	@Autowired
	job_service jobService;

//	@Test
//	void contextLoads() {
//		jobService.findCompanyJobs(1L);
//	}

}
