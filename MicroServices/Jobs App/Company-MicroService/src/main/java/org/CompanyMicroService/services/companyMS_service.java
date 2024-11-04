package org.CompanyMicroService.services;

import ReviewsMS.pojo.reviews_pojo;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import org.CompanyMicroService.Clients.JobMicroServiceClient;
import org.CompanyMicroService.Clients.ReviewMsClient;
import org.CompanyMicroService.DTOs.CompanyMsDTO;
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
    public void save(CompanyMsDTO body) {
        repo.save(toCompany(body));
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
    public companyMS_pojo saveJob(JobMsDTO jobMsDTO, String companyName) {
        try{
            //saving job in job's db
            // transferring through DTO

            ObjectId GenerateId = new ObjectId();
            jobMsDTO.setId(GenerateId);
            jobMsDTO.setCompanyName(companyName);
            // send the request to JOb MS
            jobClient.SaveJob(jobMsDTO, GenerateId);
             // find company
            companyMS_pojo company = queryService.findByCompanyName(companyName);
            assert company != null;
            // save job
            company.getJobsList().add(toJOB(jobMsDTO));
            return repo.save(company);
        }catch (Exception e){
            log.error(" -- error in saveJOB in company service --" + e );
            return null;
        }
    }

    public String Retring(){
        return "-- Retring to save Job -- ";
    }

    public void updateJOb(JobMsDTO jobMsDTO, String id) {
        try {
            ObjectId newID = new ObjectId(id);

            assert jobMsDTO != null;
            String CompanyName = jobClient.update(jobMsDTO,newID);
            // find company by name
            companyMS_pojo company = queryService.findByCompanyName(CompanyName);
            // find review in company via its id
            assert company != null;
            jobMS_pojo job = company.getJobsList().stream().filter(r -> r.getId().equals(newID)).findAny()
                    .orElseThrow(() -> new RuntimeException("-- error in update job"));
            // update job
            job.setPosts(jobMsDTO.getPosts());
            job.setLocation(jobMsDTO.getLocation());
            job.setJobTitle(jobMsDTO.getJobTitle());
            // save it
            repo.save(company);
        }catch (Exception e){
            log.error("-- error in update job");
        }
    }

    public void deleteJOb(String jobId) {
        try{
            // find company name from job
            ObjectId id = new ObjectId(jobId);
            String companyName = jobClient.delete(id);
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
    public companyMS_pojo saveReview(review_DTo review, String companyName){
        try {
            ObjectId GenerateId = new ObjectId();
            companyMS_pojo company = queryService.findByCompanyName(companyName);

            assert company != null;
            review.setCompanyName(companyName);
            review.setId(GenerateId);
            reviewClient.save(review,GenerateId);

            company.getReviewList().add(toReview(review));
            repo.save(company);
            return company;
        }catch (Exception e){
            log.error("--- error in  saveReview in COmpany Services ---");
            return null;
        }
    }

    public void updateReview(review_DTo reviewDTo, String reviewID) {
        try {
            assert reviewDTo != null;
            ObjectId id = new ObjectId(reviewID);
            // find company Name
            String CompanyName = reviewClient.update(reviewDTo,id);
            // find company by name
            companyMS_pojo company = queryService.findByCompanyName(CompanyName);
            // find review in company via its id
            reviews_pojo review = company.getReviewList().stream().filter(r -> r.getId().equals(id)).findAny()
                    .orElseThrow(() -> new RuntimeException("-- error in update job"));
            // update review
            review.setRating(reviewDTo.getRating());
            review.setReview(reviewDTo.getReview());
            // save it
            repo.save(company);
        }catch (Exception e){
            log.error("-- error in updateReview job job");
        }
    }

    public void deleteReview(String reviewId) {
        try{
            ObjectId id = new ObjectId(reviewId);
            String companyName = reviewClient.delete(id);
            companyMS_pojo company = queryService.findByCompanyName(companyName);
            assert company != null;
            company.getReviewList().removeIf(r -> r.getId().equals(id));
            repo.save(company);
        }catch (Exception e){
            log.error("-- error in deleteReview job job");
        }
    }


                         // mappers algo's
    public companyMS_pojo toCompany(CompanyMsDTO companyMsDTO){
        assert companyMsDTO != null;
        return modelMapper.map(companyMsDTO, companyMS_pojo.class);
    }
    public CompanyMsDTO toCompanyDTO(companyMS_pojo companyMSPojo){
        assert companyMSPojo != null;
        return modelMapper.map(companyMSPojo, CompanyMsDTO.class);
    }

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

                                    // rabbit mq algos
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
