package ReviewsMS.controller;


import ReviewsMS.DTOs.review_DTo;

import ReviewsMS.pojo.reviews_pojo;
import ReviewsMS.repo.review_repo;
import ReviewsMS.services.Query_service_reviews;
import ReviewsMS.services.review_service;

import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reviews")
@Slf4j
public class review_controller {

    @Autowired
    private review_repo repo;

    @Autowired
    review_service service;

    @Autowired
    private Query_service_reviews queryService;

    @PostMapping("/save/{id}")
    public void save(@RequestBody review_DTo body, @PathVariable ObjectId id) {
        try {
             service.save(body,id);
//            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        }catch (Exception e){
            log.error("-- error in save in review controller ----------- ");
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/showAll")
    public ResponseEntity<List<reviews_pojo>> showALlReviews(){
        try{
           return new ResponseEntity<>( service.findAll() , HttpStatus.ACCEPTED);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/update/{id}")
    String update(@RequestBody review_DTo reviewDTo, @PathVariable ObjectId id){
        try{
            return service.update(reviewDTo,id);
//            return new ResponseEntity<>(companyName , HttpStatus.OK);
        } catch (Exception e) {
            return null;
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/delete/{id}")
     public String delete(@PathVariable ObjectId id){
        try{
             return service.delete(id);
//            return new ResponseEntity<>(companyName , HttpStatus.OK);
        } catch (Exception e) {
            return null;
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
