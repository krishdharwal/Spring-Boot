package jobs.services;

import jobs.pojo.company_pojo;
import jobs.pojo.job_pojo;
import jobs.pojo.reviews_pojo;
import jobs.repo.company_repo;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class company_service {


    @Autowired
    private company_repo repo;

    @Autowired
    private Query_service queryService;

    @Autowired
    private review_service reviewService;

    @Autowired
    private job_service jobService;



    @Transactional
    public String saveRating(reviews_pojo review, String companyName) {
        try{
            String reviewMessage =  reviewService.save(review);
            company_pojo company = queryService.findByCompanyName(companyName);
            company.getReviewList().add(review);
            repo.save(company);
            return reviewMessage;
        }catch (Exception e){
            log.error(" -- error in saveRating in company service --" + e);
            return "-- not saved -- ";
        }
    }

    public void save(company_pojo body) {
        repo.save(body);
    }

    @Transactional
    public String saveJob(job_pojo jobBody, String companyName) {
        try{
             String saved = jobService.save(jobBody);
            company_pojo company = queryService.findByCompanyName(companyName);
            company.getJobsList().add(jobBody);
            repo.save(company);
            return saved;
        }catch (Exception e){
            log.error(" -- error in saveJOB in company service --" );
            return "-- not saved -- ";
        }
    }

    @Transactional
    public void updateJob(String companyName, ObjectId id, job_pojo UpdatedJob){
        try{
            job_pojo PreviousJob = jobService.findById(id);
            if (PreviousJob != null){
                PreviousJob.setJobTitle(UpdatedJob.getJobTitle());
                PreviousJob.setLocation(UpdatedJob.getLocation());
                PreviousJob.setPosts(UpdatedJob.getPosts());
                jobService.save(PreviousJob);
            }
        }catch (Exception e){
            log.error(" -- error in updateJob in company service --" );
        }
    }

    @Transactional
    public void updateReview(String companyName,ObjectId id,reviews_pojo reviewsPojo){
        try{
            reviews_pojo PreviousReview = reviewService.findById(id);
            if (PreviousReview != null){
                PreviousReview.setRating(reviewsPojo.getRating());
                PreviousReview.setReview(reviewsPojo.getReview());
                reviewService.save(PreviousReview);
            }
        }catch (Exception e){
            log.error(" -- error in updateReview in company service --" );
        }
    }


    // Delete part
    @Transactional
    public void DeleteJob(String companyName,ObjectId id){
        try{
            jobService.delete(id);
            company_pojo company = queryService.findByCompanyName(companyName);
            company.getJobsList().removeIf(x -> x.getId().equals(id));
            save(company);
        }catch (Exception e){
            log.error(" -- error in DeleteJob in company service --" );
        }    }

    @Transactional
    public void DeleteReview(String companyName,ObjectId id){
        try{
            reviewService.delete(id);
            company_pojo company = queryService.findByCompanyName(companyName);
            company.getReviewList().removeIf(x -> x.getId().equals(id));
            save(company);
        }catch (Exception e){
            log.error(" -- error in DeleteReview in company service --" );
        }
    }

    }