package company.clients;


import company.DTO.Job_DTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "jobClientService" , url = "http://localhost:8092/job")
public interface Job_client {

    @PostMapping("/save")
    public void SaveJob(@RequestBody Job_DTO jobDto);

    @GetMapping("/findJobs/{id}")
    public List<?> GetJob(@PathVariable Long id);

}
