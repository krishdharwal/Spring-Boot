package org.CompanyMicroService.Clients;

import org.CompanyMicroService.DTOs.JobMsDTO;

import jet.jobMicroService.pojojob.jobMS_pojo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "JobMicroServiceClient" , url = "http://localhost:8092/job")
public interface JobMicroServiceClient {

    @PostMapping("/save")
    JobMsDTO SaveJob(@RequestBody jobMS_pojo job);

}
