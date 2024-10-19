package company.clients;


import company.DTO.Job_DTO;
import org.bson.types.ObjectId;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@FeignClient(name = "jobClientService" , url = "http://localhost:8092/job")
public interface Job_client {

    @PostMapping("/save")
    public void SaveJob(@RequestBody Job_DTO jobDto);

    @GetMapping("/findJobs/{id}")
    public List<?> GetJob(@PathVariable ObjectId id);

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@RequestBody Job_DTO jobDto, @PathVariable ObjectId id);

    }
