package org.CompanyMicroService.Clients;

import org.CompanyMicroService.DTOs.JobMsDTO;

import org.bson.types.ObjectId;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "JobMicroServiceClient" , url = "${FeignClient.Job.url}")
public interface JobMicroServiceClient {

    @PostMapping("/save")
    ObjectId SaveJob(@RequestBody JobMsDTO job);

    @GetMapping("/details/{id}")
    ResponseEntity<?> details(@PathVariable ObjectId id);

    @GetMapping("/show-all")
    ResponseEntity<?> showAll();

    @PutMapping("/update/{id}")
     ResponseEntity<String> update(@RequestBody JobMsDTO body, @PathVariable ObjectId id);

    @DeleteMapping("/{id}")
     ResponseEntity<String> delete(@PathVariable ObjectId id);
}

