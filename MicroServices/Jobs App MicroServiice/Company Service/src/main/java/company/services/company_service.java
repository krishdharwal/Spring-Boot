package company.services;

import JobsService.repo.job_repo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.xstream.mapper.Mapper;
import company.clients.Job_client;
import company.pojo.company_pojo;

import company.DTO.Job_DTO;
import company.pojo.job_pojo_in_company;
import company.repo.company_repo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class company_service {


    @Autowired
    private company_repo companyRepo;

    @Autowired
    private Job_client jobClient;


//    @Transactional
//    public String saveRating(reviews_pojo review, String companyName) {
//        try{
//            String reviewMessage =  reviewService.save(review);
//            company_pojo company = repo.findByCompanyName(companyName);
//            company.getReviewList().add(review);
//            repo.save(company);
//            return reviewMessage;
//        }catch (Exception e){
//            log.error(" -- error in saveRating in company service --" + e);
//            return "-- not saved -- ";
//        }
//    }

    public void save(company_pojo body) {
        companyRepo.save(body);
    }

//    @Transactional
//    public String saveJob(job_pojo jobBody, String companyName) {
//        try{
//           jobRepo.save(jobBody);
//            company_pojo company = companyRepo.findByCompanyName(companyName);
//            company.getJobsList().add(jobBody);
//            companyRepo.save(company);
//            return "--saved--";
//        }catch (Exception e){
//            log.error(" -- error in saveJOB in company service --" );
//            return "-- not saved -- ";
//        }
//    }

//    @Transactional
//    public void updateJob(String companyName, Long id, job_pojo UpdatedJob){
//        try{
//            job_pojo PreviousJob = jobService.findById(id);
//            if (PreviousJob != null){
//                PreviousJob.setJobTitle(UpdatedJob.getJobTitle());
//                PreviousJob.setLocation(UpdatedJob.getLocation());
//                PreviousJob.setPosts(UpdatedJob.getPosts());
//                jobService.save(PreviousJob);
//            }
//        }catch (Exception e){
//            log.error(" -- error in updateJob in company service --" );
//        }
//    }

//    @Transactional
//    public void updateReview(String companyName,Long id,reviews_pojo reviewsPojo){
//        try{
//            reviews_pojo PreviousReview = reviewService.findById(id);
//            if (PreviousReview != null){
//                PreviousReview.setRating(reviewsPojo.getRating());
//                PreviousReview.setReview(reviewsPojo.getReview());
//                reviewService.save(PreviousReview);
//            }
//        }catch (Exception e){
//            log.error(" -- error in updateReview in company service --" );
//        }
//    }


//    // Delete part
//    @Transactional
//    public void DeleteJob(String companyName,Long id){
//        try{
//            jobService.delete(id);
//            company_pojo company = repo.findByCompanyName(companyName);
//            company.getJobsList().removeIf(x -> x.getId().equals(id));
//            save(company);
//        }catch (Exception e){
//            log.error(" -- error in DeleteJob in company service --" );
//        }    }
//
//    @Transactional
//    public void DeleteReview(String companyName,Long id){
//        try{
//            reviewService.delete(id);
//            company_pojo company = repo.findByCompanyName(companyName);
//            company.getReviewList().removeIf(x -> x.getId().equals(id));
//            save(company);
//        }catch (Exception e){
//            log.error(" -- error in DeleteReview in company service --" );
//        }
//    }

    public company_pojo findByCompanyName(String name, String type) {
        return companyRepo.findByCompanyName(name);
    }

    public List<company_pojo> ShowCompanyOfType(String type) {
        return companyRepo.findByType(type);
    }


    // Ultimate Shit
    @Transactional
    public String SaveJobInCompany(Long companyId, Job_DTO jobDtoo) {
        try {
            company_pojo companyPojoDB = companyRepo.findById(companyId).orElse(null);

            if (companyPojoDB != null) {
                UUID UniqueId = UUID.randomUUID();
                companyPojoDB.addJobId(UniqueId);
                jobDtoo.setLinkedId(UniqueId);
                jobDtoo.setCompanyId(companyPojoDB.getId());
//                RestTemplate restTemplate = new RestTemplate();
//
//                ResponseEntity<String> response = restTemplate.postForEntity(
//                        "http://localhost:8092/job/save", jobDtoo, String.class);

                jobClient.SaveJob(jobDtoo);

                companyRepo.save(companyPojoDB);
            }
            return "--- job saved ---";
        } catch (Exception  e){
            log.error("--- error in company services ---");
            return "-- kuch toh galat hai --";
        }
    }


    // Dopamine
    public List<?> findJobOfCompany(Long id) {
        try{
//            RestTemplate restTemplate = new RestTemplate();
//            ResponseEntity<List<?>> response = restTemplate.exchange(
//                    "http://localhost:8092/job/findJobs/"+ id,
//                    HttpMethod.GET,
//                    null,
//                    new ParameterizedTypeReference<List<?>>() {}
//            );
//            return response.getBody();

            return jobClient.GetJob(id);
        }catch (Exception e){
            log.error(" -- error in findJobOfCompany ");
            return null;
        }
    }



}