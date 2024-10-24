package org.CompanyMicroService.controller;


import ReviewsMS.pojo.reviews_pojo;
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
    public List<?> findByCompanyType(@PathVariable String type){
        try{
            return queryService.ShowAllCompanyOfType(type);
        }catch (Exception e){
            log.error("--error in find by type in controller");
            return null;
        }
    }


    @GetMapping("/findAll")
    public List<companyMS_pojo> FindAllCompanies(){
        return service.findAll();
    }
                                         // JOB MS Algo's

    @PostMapping("/job/{companyName}")
    public companyMS_pojo saveJob(@RequestBody jobMS_pojo jobBody, @PathVariable String companyName){
        return service.saveJob(jobBody,companyName);
    }


    @PutMapping("/job/update/{id}")
    public ResponseEntity<?> updateJOb(@RequestBody jobMS_pojo body, @PathVariable ObjectId JObid){
        try{
            assert body != null;
            service.updateJOb(body,JObid);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
           return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/job/{id}")
    public ResponseEntity<?> deleteJOb(@PathVariable ObjectId JobId){
        try{
            service.deleteJOb(JobId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


                                        // Review MS Algo's
    @PostMapping("/review/{companyName}")
    public ResponseEntity<?> SaveReview(@RequestBody reviews_pojo review_Body, @PathVariable String companyName){
        try {
            String s = service.saveReview(review_Body, companyName);
            return new ResponseEntity<>( s , HttpStatus.ACCEPTED);
        }catch (Exception e){
            log.error("cannot able to save review");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


    @PutMapping("/review/update")
    public ResponseEntity<?> updateReview(@RequestBody reviews_pojo review, @PathVariable ObjectId reviewID){
        try{
            service.updateReview(review , reviewID);
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/review/delete")
    public ResponseEntity<?> deleteReview(ObjectId ReviewId){
        try{
            service.deleteReview(ReviewId);
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}
