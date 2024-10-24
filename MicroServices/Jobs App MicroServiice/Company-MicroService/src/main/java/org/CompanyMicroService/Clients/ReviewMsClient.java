package org.CompanyMicroService.Clients;

import ReviewsMS.pojo.reviews_pojo;
import org.CompanyMicroService.DTOs.review_DTo;
import org.bson.types.ObjectId;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@FeignClient(name = "ReviewsClient", url = "${FeignClient.review.url}")
public interface ReviewMsClient {

    @GetMapping("/showAll")
    ResponseEntity<List<reviews_pojo>> showALlReviews();

    @PostMapping("/save")
     void save(@RequestBody review_DTo body);

    @PutMapping("/update/{reviewID}")
    String update(@RequestBody review_DTo reviewDTo, @PathVariable("reviewID") ObjectId reviewID);

    @DeleteMapping("/delete/{id}")
    ResponseEntity<String> delete(@PathVariable("id") ObjectId id);
}

