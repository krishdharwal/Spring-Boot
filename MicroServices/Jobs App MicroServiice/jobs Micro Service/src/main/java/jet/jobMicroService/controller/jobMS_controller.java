package jet.jobMicroService.controller;

import jet.jobMicroService.DTOs.JobMsDTO;
import jet.jobMicroService.pojojob.jobMS_pojo;
import jet.jobMicroService.services.jobMS_service;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/job")
public class jobMS_controller {

    @Autowired
    private jobMS_service service;

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
    public ObjectId save(@RequestBody JobMsDTO body){
        try {
             return service.save(body);
//            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        }catch (Exception e){
//            return new ResponseEntity<>(HttpStatus.I_AM_A_TEAPOT);
            return null;
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@RequestBody JobMsDTO body, @PathVariable ObjectId id){
        try {
            service.update(id,body);
            return new ResponseEntity<>("--updated--", HttpStatus.ACCEPTED);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.I_AM_A_TEAPOT);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable ObjectId id){
        try {
             String companyName = service.delete(id);
            return new ResponseEntity<>(companyName , HttpStatus.ACCEPTED);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.I_AM_A_TEAPOT);
        }
    }

}
