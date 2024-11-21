package user.service;

import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import user.Clients.movie_client;
import user.Dto.movie_reserve_dto;
import user.Dto.user_DTO;
import user.Enum.Roles_enum;
import user.pojo.movie_reserve_pojo;
import user.pojo.user_pojo;
import user.repo.user_repo;

import java.time.LocalDateTime;
import java.util.List;

import static user.Enum.Roles_enum.ADMIN;
import static user.Enum.Roles_enum.USER;

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

    @Autowired
    private static final PasswordEncoder encoder = new BCryptPasswordEncoder();


    public void save_user(user_DTO user_dto) {
        try{
            user_pojo user = toUser(user_dto);
            user.setRole(USER);
            user.setPassword(encoder.encode(user.getPassword()));
            repo.save(user);
        } catch (Exception e) {
            log.error(" -- error in save in user services -- ");
        }
    }

    public void save_admin(user_DTO user_dto) {
        try{
            user_pojo admin = toUser(user_dto);
            admin.setRole(ADMIN);
            admin.setPassword(encoder.encode(admin.getPassword()));
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

    public movie_reserve_dto toMovieReserveDto(movie_reserve_pojo data){
        assert data != null;
        return mapper.map(data, movie_reserve_dto.class);
    }

    public List<user_pojo> findAll() {
        try {
            return repo.findAll();
        }catch (Exception e){
            log.error(" in find all in user service ");
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
            userPojo = repo.save(userPojo);
            //  return the id to book the reserved seets and to send the id in mail
            return userPojo.getReservedMovies().get(0).getId();

        }catch (Exception e){
            log.error("-- error in update_User_Reserved_seats in user service --");
            return null;
        }
    }

    // book the seats that are reserved
    public void Book_seats_that_are_reserved(String name, ObjectId id) {
        try {
            user_pojo user = userQueries.findByName(name);
            assert user != null;
            // send the request to movie MS to book the reserved one
            movieClient.Book_seats_that_are_reserved(
                    user.getReservedMovies().stream().filter(x -> x.getId().equals(id)).toList().get(0)
            );
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void update_User_Movies(user_DTO userDto) {
        try{
            user_pojo user = userQueries.findByName(userDto.getName());
            user.setReservedMovies(userDto.getReservedMovies());
            repo.save(user);
        }catch (Exception e){
            log.error(" -- error in update_User_Movies in user service -- ");
        }
    }

    public void Delete_Booked_ticket(String name, ObjectId id) {
        try{
            user_pojo user = userQueries.findByName(name);
            assert  user != null;
            movie_reserve_pojo Booked_ticket = user.getReservedMovies().stream().filter(x -> x.getId().equals(id)).toList().get(0);
            // check  for the time and is the time is not gone to cancel the ticket
            // now setted for 1 hour and can be change in future via adding time to movie play in screen
            boolean check_for_time = Booked_ticket.getReservedAt().plusHours(1L).isBefore(LocalDateTime.now());

            if (check_for_time ) {
                user.getReservedMovies().removeIf(x -> x.getId().equals(id));
                repo.save(user);
                // update the movie list to again open the booked seats
                movieClient.Delete_Booked_ticket(toMovieReserveDto(Booked_ticket));
            }
            else {
                System.out.println(" -- You Cannot Cancel The Ticket Cause Time Get Out -- ");
            }
        }catch (Exception e){
            log.error(" -- error in Delete_Booked_ticket in user service -- ");
        }
    }

    public user_pojo show_user(String name) {
        return userQueries.findByName(name);
    }
}


//  user.getReservedMovies().stream().filter(x -> x.getId().equals(id)).collect(Collectors.toList()), user