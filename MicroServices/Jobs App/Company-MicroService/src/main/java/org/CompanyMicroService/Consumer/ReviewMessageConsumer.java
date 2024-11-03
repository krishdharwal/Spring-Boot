package org.CompanyMicroService.Consumer;

import org.CompanyMicroService.DTOs.review_DTo;
import org.CompanyMicroService.services.companyMS_service;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReviewMessageConsumer {

    @Autowired
    private companyMS_service companyMSService;

    @RabbitListener(queues = "CompanyRatingQueue")
    public void consumeMessage(review_DTo reviewDTo){
       companyMSService.updateCompanyReviewSetAverage(reviewDTo);
    }

}
