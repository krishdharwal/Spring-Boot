package jobs.services;

import jobs.pojo.company_pojo;
import jobs.pojo.job_pojo;
import jobs.pojo.reviews_pojo;
import jobs.repo.company_repo;
import lombok.extern.slf4j.Slf4j;
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
}
