package org.CompanyMicroService.Clients;

import org.CompanyMicroService.DTOs.JobMsDTO;

import org.bson.types.ObjectId;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "JobMicroServiceClient" , url = "${FeignClient.Job.url}")
public interface JobMicroServiceClient {

    @PostMapping("/save")
     void SaveJob(@RequestBody JobMsDTO job);

    @GetMapping("/details/{id}")
     void details(@PathVariable ObjectId id);

    @GetMapping("/show-all")
     void showAll();

    @PutMapping("/update/{id}")
     void update(@RequestBody JobMsDTO body, @PathVariable ObjectId id);

    @DeleteMapping("/{id}")
     void delete(@PathVariable ObjectId id);
}

