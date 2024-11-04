package net.dockerHive.dockerhive;

import jobs.pojo.company_pojo;
import jobs.services.Query_service;
import jobs.start_job;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest(classes = start_job.class)
public class QUery_test {

    @Autowired
    Query_service queryService;

    @Test
    void test_findCompanyByName(){
         queryService.findByCompanyName("google");
    }
}
