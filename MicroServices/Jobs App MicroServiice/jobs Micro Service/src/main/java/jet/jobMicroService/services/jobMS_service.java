package jet.jobMicroService.services;

import jet.jobMicroService.DTOs.JobMsDTO;
import jet.jobMicroService.pojojob.jobMS_pojo;
import jet.jobMicroService.repo.jobMS_repo;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class jobMS_service {
    @Autowired
    private jobMS_repo repo;

    @Autowired
    private Query_service queryService;

    @Autowired
    private ModelMapper modelMapper;

    // show all
    public List<jobMS_pojo> findAll(){
        try {
            return repo.findAll();
        }catch (Exception e){
            log.error("-- error in find all services --");
            return null;
        }
    }

    // get by id
    public jobMS_pojo findById(ObjectId id){
        try {
            jobMS_pojo users = repo.findById(id).orElse(null);
            if (users  != null) {
                return users;
            }
        }catch (Exception e){
            log.error("-- error in findbyid in  JOB services --");
            return null;
        }
        return null;
    }

    // save
    public JobMsDTO save(JobMsDTO Provided_job){
        try{
             jobMS_pojo pojo = repo.save(toJOB(Provided_job));
             return toJobDTO(pojo);

        }catch (Exception e){
            log.error("-- error in save - services" + e);
            return null;
        }
    }


    // update
    public String update(ObjectId id, JobMsDTO newJOb){
        try {
            // 1st method
            // finding the job
            jobMS_pojo jobFromDB = repo.findById(id).orElse(null);
//
            assert jobFromDB != null;
            jobFromDB.setJobTitle(newJOb.getJobTitle());
            jobFromDB.setLocation(newJOb.getLocation());
            jobFromDB.setPosts(newJOb.getPosts());
            repo.save(jobFromDB);
            return jobFromDB.getCompanyName();
            // 2nd method (Testing)
//            repo.save(toJOB(newJOb));
        }catch (Exception e){
        log.error("-- error in update in job services --");
        return null;
        }
    }


    // delete
    public String delete(ObjectId id){
        try {
            jobMS_pojo jobMSPojo = repo.findById(id).orElse(null);
            assert jobMSPojo != null;
            String companyName = jobMSPojo.getCompanyName();
            repo.deleteById(id);
            return companyName;
        }catch (Exception e){
            log.error("-- error in delete in job serices --");
            return null;
        }
    }


    // mapper
    public jobMS_pojo toJOB(JobMsDTO jobMsDTO){
        assert jobMsDTO != null;
        return modelMapper.map(jobMsDTO, jobMS_pojo.class);
    }

    public JobMsDTO toJobDTO(jobMS_pojo jobMSPojo){
        assert jobMSPojo != null;
        return modelMapper.map(jobMSPojo, JobMsDTO.class);
    }
}
