package company.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import company.DTO.Job_DTO;
import company.pojo.company_pojo;

import company.services.company_service;
import lombok.extern.slf4j.Slf4j;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/company")
@Slf4j
public class company_controller {

    @Autowired
    private company_service service;

    @PostMapping
    public String saveCompany(@RequestBody company_pojo body) {
        service.save(body);
        return "saved";
    }


    @GetMapping("/health")
    public String health() {
        return "-- company app is running --";
    }


    @PostMapping("/job/{id}")
    public String saveJob(@RequestBody Job_DTO jobBody, @PathVariable ObjectId id) throws JsonProcessingException {
        return service.SaveJobInCompany(id, jobBody);
    }


    @GetMapping("/find/{id}")
    public ResponseEntity<?> findJobsOfCompany(@PathVariable ObjectId id) {
        try {
            return new ResponseEntity<>(service.findJobOfCompany(id), HttpStatus.OK);
        } catch (Exception e) {
            log.error("error in findby name controller");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/type/{type}")
    public List<company_pojo> findByCompanyType(@PathVariable String type) {
        try {
            return service.ShowCompanyOfType(type);
        } catch (Exception e) {
            log.error("--error in find by type in controller");
            return null;
        }
    }


    // update part

    @PutMapping("/update/job/{id}")
    public ResponseEntity<?> updateJOB(@RequestBody Job_DTO jobDto,@PathVariable ObjectId id) {
        try {
            service.updateJob(jobDto,id);
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        } catch (Exception e) {
            log.error("--error in updateJOB in controller");
            return null;
        }

    }


// delete part

//    @DeleteMapping("/job/{companyName}/{id}")
//    public void deleteJob(@PathVariable String companyName,@PathVariable Long id){
//        service.DeleteJob(companyName,id);
//    }
//}




} // end