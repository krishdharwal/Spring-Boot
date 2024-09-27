package jobs.controller;

import jobs.pojo.job_pojo;
import jobs.services.job_service;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/job")
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
    public ResponseEntity<?> save(@RequestBody job_pojo body){
        try {
            service.save(body);
            return new ResponseEntity<>("--data added --", HttpStatus.ACCEPTED);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.I_AM_A_TEAPOT);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@RequestBody job_pojo body,@PathVariable ObjectId id){
        try {
            service.update(id,body);
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
