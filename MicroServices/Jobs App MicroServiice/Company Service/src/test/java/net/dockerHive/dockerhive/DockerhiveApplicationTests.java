package net.dockerHive.dockerhive;

import company.services.company_service;
import company.start_company;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = start_company.class)
class DockerhiveApplicationTests {

	@Autowired
	company_service companyService;


	@Test
	@Disabled
	void contextLoads() {
		companyService.addId(1L, 12);

	}


}
