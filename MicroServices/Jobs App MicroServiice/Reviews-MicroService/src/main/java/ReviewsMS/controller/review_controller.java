package ReviewsMS.controller;


import ReviewsMS.DTOs.review_DTo;

import ReviewsMS.repo.review_repo;
import ReviewsMS.services.review_service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reviews")
@Slf4j
public class review_controller {

    @Autowired
    private review_repo repo;

    @Autowired
    review_service service;


//    @Autowired
//    private Query_service_reviews queryService;
//
//    @GetMapping("/getReview/{name}")
//    public List<?> showCompanyReviews(@PathVariable String name){
//        try{
//            return queryService.ReviewOfCompany(name);
//        }catch (Exception e){
//            log.error("-- error in the show company reviws Controller --");
//            return null;
//        }
//    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody review_DTo body) {
        try {
            assert body != null;
            service.save(body);
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        }catch (Exception e){
            log.error("-- error in ");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
