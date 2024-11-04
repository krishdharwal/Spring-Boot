package jobs.controller;

import jobs.pojo.company_pojo;
import jobs.pojo.job_pojo;
import jobs.pojo.reviews_pojo;
import jobs.services.Query_service;
import jobs.services.company_service;
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
public class company_controller {


    @Autowired
    private Query_service queryService;
    @Autowired
    private company_service service;

    @PostMapping
    public String saveCompany(@RequestBody company_pojo body){
        service.save(body);
        return "saved";
    }

    @PostMapping("/rating/{companyName}")
    public String saveRating(@RequestBody reviews_pojo review,@PathVariable String companyName){
         String saved = service.saveRating(review,companyName);
        return saved;
    }

    @PostMapping("/job/{companyName}")
    public String saveJob(@RequestBody job_pojo jobBody, @PathVariable String companyName){
        String saved = service.saveJob(jobBody,companyName);
        return saved;
    }


    @GetMapping("/find/{name}/{type}")
    public List<company_pojo> findBYname(@PathVariable String name,@PathVariable String type){
        try {
            return queryService.findByCompanyName(name,type);
        }catch (Exception e){
            log.error("error in findby name controller");
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


    // update part
    @PutMapping("/update/job/{companyName}/{id}")
    public ResponseEntity<?> updateJOB(@PathVariable String companyName, @PathVariable ObjectId id, @RequestBody job_pojo job){
        try{
            service.updateJob(companyName,id,job);
            return new ResponseEntity<>( HttpStatus.ACCEPTED);
        }catch (Exception e){
            log.error("--error in updateJOB in controller");
            return null;
        }
    }


    // delete controllers
    @DeleteMapping("/review/{companyName}/{id}")
    public void deleteReview(@PathVariable String companyName,@PathVariable ObjectId id){
        service.DeleteReview(companyName,id);
    }

    @DeleteMapping("/job/{companyName}/{id}")
    public void deleteJob(@PathVariable String companyName,@PathVariable ObjectId id){
        service.DeleteJob(companyName,id);
    }
}
