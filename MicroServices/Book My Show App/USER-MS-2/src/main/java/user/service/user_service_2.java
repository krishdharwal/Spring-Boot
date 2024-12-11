package user.service;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;
import user.Clients.movie_client;
import user.Dto.user_DTO;
import user.pojo.user_pojo;
import user.repo.user_repo;

@Service
@Slf4j
@CrossOrigin(origins = "http://localhost:3000")
public class user_service_2 {

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private user_repo repo;

    @Autowired
    private User_Queries userQueries;

    @Autowired
    private movie_client movieClient;


    public void Book_Ticket(String movie){
        try{
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            user_pojo user = userQueries.findByName(auth.getName());
            movieClient.book_Ticket(movie, toUserDto(user));
        } catch (Exception e) {
            log.error(" -- error in book ticket in user service 2");
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

    public user_DTO toUserDto(user_pojo userPojo){
        try {
            assert userPojo != null;
            return mapper.map(userPojo, user_DTO.class);
        }catch (Exception e){
            log.error(" -- error in toUser_Dto in service --");
            return null;
        }
    }


}
