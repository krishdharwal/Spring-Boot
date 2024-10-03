package jobs.controller;

import jobs.pojo.reviews_pojo;
import jobs.repo.review_repo;
import jobs.services.Query_service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/review")
@Slf4j
public class review_controller {

    @Autowired
    private review_repo repo;

    @Autowired
    private Query_service queryService;

    @GetMapping("/getReview/{name}")
    public List<?> showCompanyReviews(@PathVariable String name){
        try{
            return queryService.ReviewOfCompany(name);
        }catch (Exception e){
            log.error("-- error in the show company reviws Controller --");
            return null;
        }
    }

    @PostMapping
    public String save(@RequestBody reviews_pojo body) {
        try {
            if (body.getRating() <= 5 && body.getRating() >= 0) {
                repo.save(body);
                return "-- saved --";
            }else {
                return "-- please give Rating in between [ 0 - 5 ]";
            }

        }catch (Exception e){
            log.error("-- error in ");
            return "-- error --";
        }
    }
}
