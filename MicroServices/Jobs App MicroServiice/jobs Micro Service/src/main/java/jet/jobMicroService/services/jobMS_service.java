package jet.jobMicroService.services;

import jet.jobMicroService.DTOs.JobMsDTO;
import jet.jobMicroService.pojojob.jobMS_pojo;
import jet.jobMicroService.repo.jobMS_repo;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
//@Slf4j
public class jobMS_service {

    private static final Logger log = LoggerFactory.getLogger(jobMS_service.class);

    @Autowired
    private jobMS_repo repo;

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
    public JobMsDTO save(jobMS_pojo Provided_job){
        try{
            jobMS_pojo SavedJob = repo.save(Provided_job);
            JobMsDTO jobMsDTO = new JobMsDTO();

//            jobMsDTO.setId(Provided_job.getId());
//            jobMsDTO.setJobTitle(Provided_job.getJobTitle());
//            jobMsDTO.setLocation(Provided_job.getLocation());
//            jobMsDTO.setPosts(Provided_job.getPosts());

            return jobMsDTO;
        }catch (Exception e){
            log.error("-- error in save - services" + e);
            return null;
        }
    }

    // update
//    public void update(ObjectId id, jobMS_pojo newUser){
//        try{
//           jobMS_pojo userFromDB =  repo.findById(id).orElse(null);
//           if (userFromDB != null){
//               userFromDB.setJob(newUser.getJob());
//               userFromDB.setUsername(newUser.getUsername());
//               userFromDB.setLocation(newUser.getLocation());
//               repo.save(userFromDB);
//           }
//        }catch (Exception e){
//        log.error("-- error in update in job services --");
//        }
//    }

    // delete
    public void delete(ObjectId id){
        try {
            repo.deleteById(id);
        }catch (Exception e){
            log.error("-- error in delete in job serices --");
        }
    }
}
