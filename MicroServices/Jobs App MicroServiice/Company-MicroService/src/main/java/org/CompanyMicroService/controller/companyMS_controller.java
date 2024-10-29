package org.CompanyMicroService.controller;

import org.CompanyMicroService.DTOs.CompanyMsDTO;
import org.CompanyMicroService.DTOs.JobMsDTO;
import org.CompanyMicroService.DTOs.review_DTo;
import org.CompanyMicroService.pojo.companyMS_pojo;
import org.CompanyMicroService.services.Query_service;
import org.CompanyMicroService.services.companyMS_service;
import lombok.extern.slf4j.Slf4j;
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
    public String saveCompany(@RequestBody CompanyMsDTO body){
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
    public companyMS_pojo saveJob(@RequestBody JobMsDTO jobBody, @PathVariable String companyName){
        return service.saveJob(jobBody,companyName);
    }


    @PutMapping("/job/update/{id}")
    public ResponseEntity<?> updateJOb(@RequestBody JobMsDTO body, @PathVariable String id){
        try{
            assert body != null;
            service.updateJOb(body,id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
           return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/job/{id}")
    public ResponseEntity<?> deleteJOb(@PathVariable String id){
        try{
            service.deleteJOb(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


                                        // Review MS Algo's
    @PostMapping("/review/{companyName}")
    public ResponseEntity<?> SaveReview(@RequestBody review_DTo review_Body, @PathVariable String companyName){
        try {
            companyMS_pojo companyMSPojo =  service.saveReview(review_Body, companyName);
            return new ResponseEntity<>( companyMSPojo , HttpStatus.ACCEPTED);
        }catch (Exception e){
            log.error("cannot able to save review");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


    @PutMapping("/review/update/{id}")
    public ResponseEntity<?> updateReview(@RequestBody review_DTo review, @PathVariable String id){
        try{
            service.updateReview(review , id);
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/review/delete/{id}")
    public ResponseEntity<?> deleteReview(@PathVariable String id){
        try{
            service.deleteReview(id);
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


}
