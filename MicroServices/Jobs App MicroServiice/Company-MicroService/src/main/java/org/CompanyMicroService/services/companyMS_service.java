package org.CompanyMicroService.services;

import ReviewsMS.pojo.reviews_pojo;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import org.CompanyMicroService.Clients.JobMicroServiceClient;
import org.CompanyMicroService.Clients.ReviewMsClient;
import org.CompanyMicroService.DTOs.JobMsDTO;
import org.CompanyMicroService.DTOs.review_DTo;
import org.CompanyMicroService.pojo.companyMS_pojo;
import org.CompanyMicroService.repo.companyMS_repo;
import jet.jobMicroService.pojojob.jobMS_pojo;
import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.math.ec.ECConstants;
import org.bson.types.ObjectId;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.TransientDataAccessException;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class companyMS_service {
    @Autowired
    private companyMS_repo repo;

    @Autowired
    private Query_service queryService;

    @Autowired
    private JobMicroServiceClient jobClient;

    @Autowired
    private ReviewMsClient reviewClient;

    @Autowired
    private ModelMapper modelMapper;


                                // company algo's

    // rate limiter is used to limit the rate of calls
    @RateLimiter(name = "companyBreaker" , fallbackMethod = "DenialOfService")
    public void save(companyMS_pojo body) {
        repo.save(body);
    }

    public String DenialOfService(Exception e){
        return "So many Attempts to create a account";
    }

    @CircuitBreaker(name = "companyBreaker", fallbackMethod = "findAllFallBack")
    public List<companyMS_pojo> findAll() {
        return repo.findAll();
    }

    // fall back mechanism if the findAll method fails
    public String findAllFallBack(Exception e){
        return "Circuit Breaker ->> findAll method is not working ";
    }

                                   // JOb algo's

    // Retry is used to retry the request as many times as defined in properties file
    // if request is not accepted it will try that many times.
    @Transactional
    @Retry(name = "companyBreaker" , fallbackMethod = "Retring")
    public companyMS_pojo saveJob(jobMS_pojo jobBody, String companyName) {
        try{
            //saving job in job's db
            // transferring through DTO

            ObjectId GenerateId = new ObjectId();
            jobBody.setId(GenerateId);
            jobBody.setCompanyName(companyName);
            // send the request to JOb MS
            jobClient.SaveJob(toJobDTO(jobBody), GenerateId);
             // find company
            companyMS_pojo company = queryService.findByCompanyName(companyName);
            assert company != null;
            // save job
            System.out.println(jobBody.getId());
            System.out.println(jobBody);
            company.getJobsList().add(jobBody);
            return repo.save(company);
        }catch (Exception e){
            log.error(" -- error in saveJOB in company service --" + e );
            return null;
        }
    }

    public String Retring(){
        return "-- Retring to save Job -- ";
    }

    public void updateJOb(jobMS_pojo jobMSPojo, ObjectId id) {
        try {
            assert jobMSPojo != null;
            String CompanyName = jobClient.update(toJobDTO(jobMSPojo),id);
            // find company by name
            companyMS_pojo company = queryService.findByCompanyName(CompanyName);
            // find review in company via its id
            assert company != null;
            jobMS_pojo job = company.getJobsList().stream().filter(r -> r.getId().equals(id)).findAny()
                    .orElseThrow(() -> new RuntimeException("-- error in update job"));
            // update job
            job.setPosts(jobMSPojo.getPosts());
            job.setLocation(jobMSPojo.getLocation());
            job.setJobTitle(jobMSPojo.getJobTitle());
            // save it
            repo.save(company);
        }catch (Exception e){
            log.error("-- error in update job");
        }
    }

    public void deleteJOb(ObjectId jobId) {
        try{
            // find company name from job
            String companyName = jobClient.delete(jobId);
            // find company
            companyMS_pojo company = queryService.findByCompanyName(companyName);
            // delete job
            assert company != null;
            company.getJobsList().removeIf(r -> r.getId().equals(jobId));
            repo.save(company);
        }catch (Exception e){
            log.error("-- error in delete job job");
        }
    }


    // Reviews Algo's
    @Retryable(value = TransientDataAccessException.class, maxAttempts = 3)
    public companyMS_pojo saveReview(reviews_pojo review, String companyName){
        try {
            ObjectId GenerateId = new ObjectId();
            companyMS_pojo company = queryService.findByCompanyName(companyName);

            assert company != null;
            review.setCompanyName(companyName);
            review.setId(GenerateId);
            reviewClient.save(toReviewDTO(review),GenerateId);

            company.getReviewList().add(review);
            repo.save(company);
            return company;
        }catch (Exception e){
            log.error("--- error in  saveReview in COmpany Services ---");
            return null;
        }
    }

    public void updateReview(reviews_pojo reviewsPojo, ObjectId reviewID) {
        try {
            assert reviewsPojo != null;
            // find company Name
            String CompanyName = reviewClient.update(toReviewDTO(reviewsPojo),reviewID);
            // find company by name
            companyMS_pojo company = queryService.findByCompanyName(CompanyName);
            // find review in company via its id
            reviews_pojo review = company.getReviewList().stream().filter(r -> r.getId().equals(reviewID)).findAny()
                    .orElseThrow(() -> new RuntimeException("-- error in update job"));
            // update review
            review.setRating(reviewsPojo.getRating());
            review.setReview(reviewsPojo.getReview());
            // save it
            repo.save(company);
        }catch (Exception e){
            log.error("-- error in updateReview job job");
        }
    }

    public void deleteReview(ObjectId reviewId) {
        try{
            String companyName = reviewClient.delete(reviewId);
            companyMS_pojo company = queryService.findByCompanyName(companyName);
            assert company != null;
            company.getReviewList().removeIf(r -> r.getId().equals(reviewId));
            repo.save(company);
        }catch (Exception e){
            log.error("-- error in deleteReview job job");
        }
    }


                         // mappers algo's
    public jobMS_pojo toJOB(JobMsDTO dto){
        assert dto != null;
        return modelMapper.map(dto, jobMS_pojo.class);
    }

    public JobMsDTO toJobDTO(jobMS_pojo job){
        assert job != null;
        return modelMapper.map(job,JobMsDTO.class);
    }

    public reviews_pojo toReview(review_DTo reviewDTo){
        assert reviewDTo != null;
        return modelMapper.map(reviewDTo, reviews_pojo.class);

    }

    public review_DTo toReviewDTO(reviews_pojo reviewsPojo){
        assert reviewsPojo != null;
        return modelMapper.map(reviewsPojo,review_DTo.class);
    }

    public void updateCompanyReviewSetAverage(review_DTo reviewDTo) {
        // update the average number in company
        try {
            assert reviewDTo != null;
            companyMS_pojo companyMSPojo = queryService.findByCompanyName(reviewDTo.getCompanyName());
            double avg = companyMSPojo.getReviewList().stream().mapToDouble(reviews_pojo::getRating).average().orElse(0.0);
            companyMSPojo.setAverageRating(avg);
            repo.save(companyMSPojo);
        }catch (Exception e){
            log.error("--- error in updateCompanyReviewSetAverage in company ms service --- ");
      }
    }
}
