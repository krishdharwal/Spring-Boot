package org.CompanyMicroService.services;

import org.CompanyMicroService.Clients.JobMicroServiceClient;
import org.CompanyMicroService.DTOs.JobMsDTO;
import org.CompanyMicroService.pojo.companyMS_pojo;
import org.CompanyMicroService.repo.companyMS_repo;
import jet.jobMicroService.pojojob.jobMS_pojo;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public void save(companyMS_pojo body) {
        repo.save(body);
    }


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

    public List<companyMS_pojo> findAll() {
       return repo.findAll();
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