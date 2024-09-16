package learning.Services;

import learning.Mongo_Repositories.user_repo;
import learning.POJO.user;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
@Slf4j
public class user_service {
    @Autowired
   private user_repo repo;


    @Autowired
    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public List<user> findAll() {
        return repo.findAll();
    }

    public void save(user body){
        try {
            body.setPassword(passwordEncoder.encode(body.getPassword()));
            body.setRoles(Arrays.asList("USER"));
            repo.save(body);
        }catch (Exception e){
            log.trace(" something wrong about the {}",body.getUsername());
            log.debug(" something wrong about the {}",body.getUsername());
            log.info(" something wrong about the {}",body.getUsername());
            log.warn(" something wrong about the {}",body.getUsername());
            log.error(" something wrong about the {}",body.getUsername());

        }
    }

    public void save_simple(user body){
        repo.save(body);
    }



    public user findByname(String name){
        return repo.findByUsername(name);
    }

    public void deleteById(ObjectId id) {
        repo.deleteById(id);
    }

    public void save_Admin(user body) {
        body.setPassword(passwordEncoder.encode(body.getPassword()));
        body.setRoles(Arrays.asList("ADMIN","USER"));
        repo.save(body);
    }
}
