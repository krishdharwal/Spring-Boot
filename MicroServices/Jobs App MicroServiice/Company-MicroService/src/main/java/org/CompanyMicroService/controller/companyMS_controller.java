package org.CompanyMicroService.controller;

import ReviewsMS.pojo.reviews_pojo;
import org.CompanyMicroService.DTOs.JobMsDTO;
import org.CompanyMicroService.pojo.companyMS_pojo;
import org.CompanyMicroService.services.Query_service;
import org.CompanyMicroService.services.companyMS_service;
import jet.jobMicroService.pojojob.jobMS_pojo;

import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/company")
@Slf4j
public class companyMS_controller {

    @Autowired
    private Query_service queryService;
    @Autowired
    private companyMS_service service;

                                // Company MS Algo's
    @PostMapping
    public String saveCompany(@RequestBody companyMS_pojo body){
        service.save(body);
        return "saved";
    }

    @GetMapping("/find/{name}/{type}")
    public List<companyMS_pojo> findBYname(@PathVariable String name, @PathVariable String type){
        try {
            return queryService.findByCompanyName(name,type);
        }catch (Exception e){
            log.error("error in find by name controller");
            return null;
        }
    }

    @GetMapping("/type/{type}")
    public List<?> findByType(@PathVariable String type){
        try{
            return queryService.ShowAllCompanyOfType(type);
        }catch (Exception e){
            log.error("--error in find by type in controller");
            return null;
        }
    }


    @GetMapping("/findAll")
    public List<companyMS_pojo> FindAll(){
        return service.findAll();
    }

    // update part
//    @PutMapping("/update/job/{companyName}/{id}")
//    public ResponseEntity<?> updateJOB(@PathVariable String companyName, @PathVariable ObjectId id, @RequestBody jobMS_pojo job){
//        try{
//            service.updateJob(companyName,id,job);
//            return new ResponseEntity<>( HttpStatus.ACCEPTED);
//        }catch (Exception e){
//            log.error("--error in updateJOB in controller");
//            return null;
//        }
//    }


    // delete controllersjob
//    @DeleteMapping("/job/{companyName}/{id}")
//    public void deleteJob(@PathVariable String companyName,@PathVariable ObjectId id){
//        service.DeleteJob(companyName,id);
//    }

                                         // JOB MS Algo's

    @PostMapping("/job/{companyName}")
    public companyMS_pojo saveJob(@RequestBody jobMS_pojo jobBody, @PathVariable String companyName){
        return service.saveJob(jobBody,companyName);
    }

    @GetMapping("/details/{id}")
    public ResponseEntity<?> details(@PathVariable ObjectId id){

    }

    @GetMapping("/show-all")
    public ResponseEntity<?> showAll(){

    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@RequestBody JobMsDTO body, @PathVariable ObjectId id){

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable ObjectId id){

    }










                                        // Review MS Algo's
    @PostMapping("review/{companyName}")
    public ResponseEntity<?> SaveReview(@RequestBody reviews_pojo review_Body, @PathVariable String CompanyName){
        try {
            String s = service.saveReview(review_Body, CompanyName);
            return new ResponseEntity<>( s , HttpStatus.ACCEPTED);
        }catch (Exception e){
            log.error("cannot able to save review");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
