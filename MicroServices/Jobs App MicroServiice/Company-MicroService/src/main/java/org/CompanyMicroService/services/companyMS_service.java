package org.CompanyMicroService.services;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import org.CompanyMicroService.Clients.JobMicroServiceClient;
import org.CompanyMicroService.DTOs.JobMsDTO;
import org.CompanyMicroService.pojo.companyMS_pojo;
import org.CompanyMicroService.repo.companyMS_repo;
import jet.jobMicroService.pojojob.jobMS_pojo;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
    private ModelMapper modelMapper;

    // rate limiter is used to limit the rate of calls
    @RateLimiter(name = "companyBreaker" , fallbackMethod = "DenialOfService")
    public void save(companyMS_pojo body) {
        repo.save(body);
    }

    public String DenialOfService(Exception e){
        return "So many Attempts to create a account";
    }


    // Retry is used to retry the request as many times as defined in properties file
    // if request is not accepted it will try that many times.
    @Transactional
    @Retry(name = "companyBreaker" , fallbackMethod = "Retring")
    public companyMS_pojo saveJob(jobMS_pojo jobBody, String companyName) {
        try{
            //saving job in job's db
             JobMsDTO jobMsDTO = jobClient.SaveJob(jobBody);
             // find company
            companyMS_pojo company = queryService.findByCompanyName(companyName);
            assert company != null;
            // map jobDto to job
            jobMS_pojo jobMSPojo = toJOB(jobMsDTO);
            company.getJobsList().add(jobMSPojo);
            return repo.save(company);

        }catch (Exception e){
            log.error(" -- error in saveJOB in company service --" + e );
            return null;
        }
    }

    public String Retring(){
        return "-- Retring to save Job -- ";
    }



    @CircuitBreaker(name = "companyBreaker", fallbackMethod = "findAllFallBack")
    public List<companyMS_pojo> findAll() {
       return repo.findAll();
    }

    // fall back mechanism if the findAll method fails
    public String findAllFallBack(Exception e){
        return "Circuit Breaker ->> findAll method is not working ";
    }


    // mapping
    public jobMS_pojo toJOB(JobMsDTO dto){
        assert dto != null;
        return modelMapper.map(dto, jobMS_pojo.class);
    }

    public JobMsDTO toDTO(jobMS_pojo job){
        assert job != null;
        return modelMapper.map(job,JobMsDTO.class);
    }

}