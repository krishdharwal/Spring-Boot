package user.service;

import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;
import user.Clients.movie_client;
import user.Dto.user_DTO;
import user.Enum.Roles_enum;
import user.pojo.user_pojo;
import user.repo.user_repo;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class user_service {

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private user_repo repo;


    @Autowired
    private User_Queries userQueries;

    @Autowired
    private movie_client movieClient;


    public void save_user(user_DTO user_dto) {
        try{
            user_pojo user = toUser(user_dto);
            user.setRole(Roles_enum.USER);
            repo.save(user);
        } catch (Exception e) {
            log.error(" -- error in save in user services -- ");
        }
    }

    public void save_admin(user_DTO user_dto) {
        try{
            user_pojo admin = toUser(user_dto);
            admin.setRole(Roles_enum.ADMIN);
            repo.save(admin);
        } catch (Exception e) {
            log.error(" -- error in save in admin in user  services -- ");
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

    public List<user_pojo> findAll() {
        try {
            return repo.findAll();
        }catch (Exception e){
            new NotFoundException(" in find all in user service ");
            return null;
        }
    }

    public void update_User(String name, user_DTO userDto) {
        user_pojo user = userQueries.findByName(name);
        assert user != null;
        user.setName(userDto.getName());
        user.setPassword(userDto.getPassword());
    }

    public user_DTO findByName(String name) {
        try {
            user_pojo user = userQueries.findByName(name);
            assert user != null;
            return toUserDto(user);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public ObjectId update_User_Reserved_seats(user_DTO user) {
        try {
            // set the reserved movie and seats
            user_pojo userPojo = userQueries.findByName(user.getName());
            userPojo.setReservedMovies(user.getReservedMovies());
            user_pojo userPojo2 = repo.save(userPojo);
            //  return the id to book the reserved seets and to send the id in mail
            return userPojo2.getReservedMovies().get(0).getId();

        }catch (Exception e){
            log.error("-- error in update_User_Reserved_seats in user service --");
            return null;
        }
    }

    public void Book_seats_that_are_reserved(String name, ObjectId id) {
        try {
            user_pojo user = userQueries.findByName(name);
            assert user != null;
            movieClient.Book_seats_that_are_reserved(
                    user.getReservedMovies().stream().filter(x -> x.getId().equals(id)).toList().get(0), user
            );
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}


//  user.getReservedMovies().stream().filter(x -> x.getId().equals(id)).collect(Collectors.toList()), user