package org.CompanyMicroService.Clients;

import ReviewsMS.pojo.reviews_pojo;
import org.CompanyMicroService.DTOs.review_DTo;
import org.bson.types.ObjectId;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "Reviews Client" , url = "${FeignClients.review.url}")
public interface ReviewMsClient {

    @GetMapping("/showAll")
    public ResponseEntity<List<reviews_pojo>> showALlReviews();

    @PostMapping
    void save(@RequestBody review_DTo review);

    @PutMapping
    public ResponseEntity<?> update(@RequestBody ReviewsMS.DTOs.review_DTo reviewDTo);

    @DeleteMapping
    public ResponseEntity<?> delete(ObjectId id);

}
