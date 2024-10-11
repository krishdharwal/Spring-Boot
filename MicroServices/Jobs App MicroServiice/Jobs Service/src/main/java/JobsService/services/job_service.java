package JobsService.services;
import JobsService.pojo.job_pojo;
import JobsService.repo.job_repo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class job_service {

    @Autowired
    private job_repo repo;

    // show all
    public List<job_pojo> findAll(){
        try {
            return repo.findAll();
        }catch (Exception e){
            log.error("-- error in find all services --");
            return null;
        }
    }

    // get by id
    public job_pojo findById(Long id){
        try {
            job_pojo users = repo.findById(id).orElse(null);
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
    public String save(job_pojo user){
        try{
            repo.save(user);
            return "-- job added --";
        }catch (Exception e){
            log.error("-- error in save - services" + e);
            return "-- error in save - services";
        }
    }

    // update
//    public void update(ObjectId id, job_pojo newUser){
//        try{
//           job_pojo userFromDB =  repo.findById(id).orElse(null);
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
    public void delete(Long id){
        try {
            repo.deleteById(id);
        }catch (Exception e){
            log.error("-- error in delete in job serices --");
        }

    }


}
