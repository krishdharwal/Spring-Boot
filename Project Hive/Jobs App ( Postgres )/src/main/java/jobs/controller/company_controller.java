package jobs.controller;

import jobs.pojo.company_pojo;
import jobs.pojo.job_pojo;
import jobs.pojo.reviews_pojo;
import jobs.services.company_service;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;


@RestController
@RequestMapping("/company")
@Slf4j
public class company_controller {


//    @Autowired
//    private Query_service queryService;
    @Autowired
    private company_service service;

    @GetMapping("/all")
    public List<company_pojo> findAll(){
        return service.findALl();
    }


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
    public String saveJob(@RequestBody job_pojo jobBody, @PathVariable Long companyName){
        String saved = service.saveJob(jobBody,companyName);
        return saved;
    }

@Transactional
    @GetMapping("/find/{name}")
    public List<company_pojo>  findBYname(@PathVariable String name){
        try
        {
            return service.findByCompanyName(name);

        }catch (Exception e){
            log.error("error in findby name controller");
            return null;
        }
    }

    @GetMapping("/type/{type}")
    public List<company_pojo> findByType(@PathVariable String type){
        try{
            return service.ShowCompanyOfType(type);
        }catch (Exception e){
            log.error("--error in find by type in controller");
            return null;
        }
    }


    // update part
    @PutMapping("/update/job/{companyName}/{id}")
    public ResponseEntity<?> updateJOB(@PathVariable String companyName, @PathVariable Long id, @RequestBody job_pojo job){
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
    public void deleteReview(@PathVariable String companyName,@PathVariable Long id){
        service.DeleteReview(companyName,id);
    }

    @DeleteMapping("/job/{companyName}/{id}")
    public void deleteJob(@PathVariable String companyName,@PathVariable Long id){
        service.DeleteJob(companyName,id);
    }
}
