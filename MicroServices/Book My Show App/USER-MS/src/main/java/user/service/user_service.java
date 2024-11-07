package user.service;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import user.Dto.user_DTO;
import user.pojo.user_pojo;
import user.repo.user_repo;

@Slf4j
@Service
public class user_service {

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private user_repo repo;

    public void save(user_DTO user) {
        try{
            repo.save(toUser(user));
        } catch (Exception e) {
            log.error(" -- error in save in user services -- ");
        }
    }

    public user_pojo toUser(user_DTO userDto){
        try {
            assert userDto != null;
            return mapper.map(userDto, user_pojo.class);
        }catch (Exception e){
            log.error(" -- error in toUser in service --");
            return null;
        }
    }
}
