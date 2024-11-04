package jobs.services;

import jobs.pojo.company_pojo;
import jobs.pojo.job_pojo;
import jobs.pojo.reviews_pojo;
import jobs.repo.company_repo;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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


    @javax.transaction.Transactional
    public List<company_pojo> findALl(){
        return repo.findAll();
    }

    @Transactional
    public String saveRating(reviews_pojo review, String companyName) {
        try{
            String reviewMessage =  reviewService.save(review);
           company_pojo company = repo.findByCompany(companyName);
//            company.getReviewList().add(review);
//            repo.save(company);
            return reviewMessage;
        }catch (Exception e){
            log.error(" -- error in saveRating in company service --" + e);
            return "-- not saved -- ";
        }
    }

    public void save(company_pojo body) {
        assert body != null;
        repo.save(body);
    }

    @Transactional
    public String saveJob(job_pojo jobBody, Long companyName) {
        try{
             String saved = jobService.save(jobBody);
             company_pojo company = repo.findById(companyName).orElse(null);
             if (company != null) {

                 company.getJobsList().add(jobBody);
             }
            assert company != null;
            System.out.println(company.getJobsList());
            repo.save(company);
            return saved;
        }catch (Exception e){
            log.error(" -- error in saveJOB in company service --" );
            return "-- not saved -- ";
        }
    }

    @Transactional
    public void updateJob(String companyName, Long id, job_pojo UpdatedJob){
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
    public void updateReview(String companyName,Long id,reviews_pojo reviewsPojo){
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
    public void DeleteJob(String companyName,Long id){
        try{
            jobService.delete(id);
           company_pojo company = repo.findByCompany(companyName);
            company.getJobsList().removeIf(x -> x.getId().equals(id));
//            save(company);
        }catch (Exception e){
            log.error(" -- error in DeleteJob in company service --" );
        }    }

    @Transactional
    public void DeleteReview(String companyName,Long id){
        try{
            reviewService.delete(id);
          company_pojo company = repo.findByCompany(companyName);
//            company.getReviewList().removeIf(x -> x.getId().equals(id));
//            save(company);
        }catch (Exception e){
            log.error(" -- error in DeleteReview in company service --" );
        }
    }

    @javax.transaction.Transactional
    public List<company_pojo> findByCompanyName(String name) {
        List<company_pojo> companyPojos =  queryService.findByCompanyName(name);
        for (company_pojo pojo : companyPojos) {
            Hibernate.initialize(pojo.getJobsList());
            System.out.println(pojo);
        }
        return companyPojos;
    }

    public List<company_pojo> ShowCompanyOfType(String type) {
        return repo.findByType(type);
    }
}



//@javax.transaction.Transactional
//public company_pojo findByCompanyName(Long name) {
//    return repo.findById(name).orElse(null);
//}