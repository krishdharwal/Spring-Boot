package org.CompanyMicroService.Clients;

import org.CompanyMicroService.DTOs.review_DTo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "Reviews Client" , url = "${FeignClients.review.url}")
public interface ReviewMsClient {

    @PostMapping
    void save(@RequestBody review_DTo review);

}
