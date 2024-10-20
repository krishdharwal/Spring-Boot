package org.CompanyMicroService.controller;

import org.CompanyMicroService.pojo.companyMS_pojo;
import org.CompanyMicroService.services.Query_service;
import org.CompanyMicroService.services.companyMS_service;
import jet.jobMicroService.pojojob.jobMS_pojo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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

    @PostMapping
    public String saveCompany(@RequestBody companyMS_pojo body){
        service.save(body);
        return "saved";
    }



    @PostMapping("/job/{companyName}")
    public companyMS_pojo saveJob(@RequestBody jobMS_pojo jobBody, @PathVariable String companyName){
        return service.saveJob(jobBody,companyName);
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
}
