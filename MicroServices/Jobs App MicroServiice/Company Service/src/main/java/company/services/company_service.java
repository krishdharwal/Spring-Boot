package company.services;

import company.clients.Job_client;
import company.pojo.company_pojo;

import company.DTO.Job_DTO;
import company.repo.companyMS_repo;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class company_service {


    @Autowired
    private companyMS_repo companyRepo;

    @Autowired
    private Job_client jobClient;


    public void save(company_pojo body) {
        companyRepo.save(body);
    }


    @Transactional
    public void updateJob( Job_DTO jobDto,ObjectId id) {
        try {
            jobClient.update(jobDto, id);
        } catch (Exception e) {
            log.error(" -- error in updateJob in company service --");
        }
    }

//
//    public company_pojo findByCompanyName(String name, String type) {
//        return companyRepo.findByCompanyName(name);
//    }

    public List<company_pojo> ShowCompanyOfType(String type) {
        return companyRepo.findByType(type);
    }


    // Ultimate Shit
//    @Transactional
//    public String SaveJobInCompany(Long companyId, Job_DTO jobDtoo) {
//        try {
//            company_pojo companyPojoDB = companyRepo.findById(companyId).orElse(null);
//
//            if (companyPojoDB != null) {
//                UUID UniqueId = UUID.randomUUID();
//                companyPojoDB.addJobId(UniqueId);
//                jobDtoo.setLinkedId(UniqueId);
//                jobDtoo.setCompanyId(companyPojoDB.getId());
//
//                jobClient.SaveJob(jobDtoo);
//
//                companyRepo.save(companyPojoDB);
//            }
//            return "--- job saved ---";
//        } catch (Exception  e){
//            log.error("--- error in company services ---");
//            return "-- kuch toh galat hai --";
//        }
//    }


    // Dopamine
    public List<?> findJobOfCompany(ObjectId id) {
        try{
            return jobClient.GetJob(id);
        }catch (Exception e){
            log.error(" -- error in findJobOfCompany ");
            return null;
        }
    }




    @Transactional
    public String SaveJobInCompany(ObjectId companyId, Job_DTO jobDtoo) {
        try {
            company_pojo companyPojoDB = companyRepo.findById(companyId).orElse(null);

            if (companyPojoDB != null) {
                UUID UniqueId = UUID.randomUUID();
                jobDtoo.setLinkedId(UniqueId);

                jobClient.SaveJob(jobDtoo);

                companyRepo.save(companyPojoDB);
            }
            return "--- job saved ---";
        } catch (Exception  e){
            log.error("--- error in company services ---");
            return "-- kuch toh galat hai --";
        }
    }


} // end point