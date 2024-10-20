package JobsService.controller;

import JobsService.DTO.Job_DTO;
import JobsService.pojo.job_pojo;
import JobsService.services.job_service;

import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/job")
@Slf4j
public class job_controller {

    @Autowired
    private job_service service;

    @GetMapping("/health")
    public String health(){
        return "--job app is ready sir ";
    }

    @GetMapping("/details/{id}")
    public ResponseEntity<?> details(@PathVariable ObjectId id){
        try {
            return new ResponseEntity<>(service.findById(id), HttpStatus.ACCEPTED);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.I_AM_A_TEAPOT);
        }
    }

    @GetMapping("/show-all")
    public ResponseEntity<?> showAll(){
        try {
            return new ResponseEntity<>(service.findAll(), HttpStatus.ACCEPTED);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.I_AM_A_TEAPOT);
        }
    }

    @PostMapping("/save")
    public ResponseEntity<String> saveJob(@RequestBody Job_DTO jobDto) {
        try {
            job_pojo job = new job_pojo();  // Create a new Job entity and map fields from DTO
            job.setJobTitle(jobDto.getJobTitle());
            job.setLocation(jobDto.getLocation());
            job.setCompanyId(jobDto.getCompanyId());
            job.setLinkedId(jobDto.getLinkedId());
            job.setPosts(jobDto.getPosts());

            // Save job in DB
            service.save(job);

            return ResponseEntity.ok("--data added --");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("--error occurred in save job  --");
        }
    }

    @GetMapping("/findJobs/{id}")
    public List<job_pojo> findCompanyJobs(@PathVariable ObjectId id){
        try{
            return service.findCompanyJobs(id);
        }catch (Exception e){
            log.error(" -- errorr in findCompanyJobs -- ");
            return null;
        }
    }




    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@RequestBody Job_DTO jobDto,@PathVariable UUID id){
        try {
//            service.update(id,body);
            return new ResponseEntity<>("--updated--", HttpStatus.ACCEPTED);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.I_AM_A_TEAPOT);
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable ObjectId id){
        try {
            service.delete(id);
            return new ResponseEntity<>("---deleted---" , HttpStatus.ACCEPTED);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.I_AM_A_TEAPOT);
        }
    }

}
