package movies.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import movies.Dto.movie_DTO;
import movies.Dto.movie_reserve_dto;
import movies.Dto.user_DTO;
import movies.Enum.Ticket_Status;
import movies.clients.screen_client;
import movies.clients.user_client;
import movies.pojo.movie_pojo;
import movies.pojo.movie_reserve_pojo;
import movies.pojo.user_pojo;
import movies.repo.movie_repo;
import org.bson.types.ObjectId;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import org.springframework.stereotype.Service;
import movies.Dto.screen_DTO;
import movies.pojo.screen_pojo;

import java.time.LocalDateTime;
import java.util.*;

@Service
@Slf4j
public class movie_service {

    @Autowired
    private Scheduling_service schedulingService;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private movie_repo repo;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private screen_client screenClient;

    @Autowired
    private Movie_Query movieQuery;

    @Autowired
    private user_client userClient;

    // Save a movie
    public Map<String, String> save(movie_DTO movieData) {
        try {
            // dummy data
            movieData.setPrice(199.9f);
            movieData.setLanguage(" hindi / english ");

            repo.save(toMovie(movieData));
            return Map.of("message", "Movie saved successfully");
        } catch (Exception e) {
            log.error("Error saving movie: {}", e.getMessage());
            throw new RuntimeException("Failed to save movie");
        }
    }

    private movie_pojo toMovie(movie_DTO movieData) {
        assert movieData != null;
        return mapper.map(movieData, movie_pojo.class);
    }

    private screen_pojo toScreen(screen_DTO screen) {
        assert screen != null;
        return mapper.map(screen, screen_pojo.class);
    }


    // Assign a hall to a movie
    public Map<String, String> set_hall_in_movie(screen_DTO screen, String movieName) {
        try {
            if (screen != null) {

                //dummy data
                screen.setName("hall");

                screen.setSeatsList(Collections.nCopies(screen.getTotalSeats(), Ticket_Status.OPENED));
                movie_pojo movie = movieQuery.find_Movie_By_Name(movieName);
                if (movie != null) {
//                    screenClient.Save_Hall(screen);
                    movie.getHall().add(toScreen(screen));
                    repo.save(movie);
                    return Map.of("message", "Hall added successfully to the movie");
                } else {
                    return Map.of("error", "Movie not found");
                }
            }
            return Map.of("error", "Invalid screen data");
        } catch (Exception e) {
            log.error("Error in set_hall_in_movie: {}", e.getMessage());
            throw new RuntimeException("Failed to assign hall to movie");
        }
    }



    // Get movie details
    public movie_pojo get_movie_details(String movieName) {
        try {
            movie_pojo movie = movieQuery.find_Movie_By_Name(movieName);
            if (movie != null) {
                return movie;
            } else {
                throw new RuntimeException("Movie not found");
            }
        } catch (Exception e) {
            log.error("Error fetching movie details: {}", e.getMessage());
            throw new RuntimeException("Failed to fetch movie details");
        }
    }

    // Book movie tickets
    public Map<String, Object> book_movie_hall(String movieName, user_DTO userDTO, int hallNumber, List<Integer> seatNumbers) {
        try {
            movie_pojo movie = movieQuery.find_Movie_By_Name(movieName);
            if (movie == null) {
                return Map.of("error", "Movie not found");
            }

            if (hallNumber <= 0 || hallNumber > movie.getHall().size()) {
                return Map.of("error", "Invalid hall number");
            }

            screen_pojo selectedHall = movie.getHall().get(hallNumber - 1);
            List<Ticket_Status> seatsList = selectedHall.getSeatsList();
            List<Integer> bookedSeats = new ArrayList<>();

            for (int seat : seatNumbers) {
                if (seat > 0 && seat <= seatsList.size() && seatsList.get(seat - 1).equals(Ticket_Status.OPENED)) {
                    seatsList.set(seat - 1, Ticket_Status.BOOKED);
                    bookedSeats.add(seat);
                } else {
                    return Map.of("error", "Seat " + seat + " is not available");
                }
            }

            float totalPrice = bookedSeats.size() * movie.getPrice();
            movie_reserve_pojo reservation = Set_movie_reserve_pojo_Details(
                    movie.getName(), bookedSeats, totalPrice, hallNumber, Ticket_Status.BOOKED);

            user_pojo user = toUser(userDTO);
            user.getReservedMovies().add(reservation);
            Update_User_Movie(user);
            update_Movie_Hall_Seats_After_Confirmation(movie, bookedSeats, hallNumber - 1, Ticket_Status.BOOKED);

            return Map.of(
                    "message", "Booking successful",
                    "totalPrice", totalPrice,
                    "bookedSeats", bookedSeats
            );

        } catch (Exception e) {
            log.error("Error booking movie hall: {}", e.getMessage());
            throw new RuntimeException("Failed to book movie tickets");
        }
    }

    // Helper methods and utilities

    private void update_Movie_Hall_Seats_After_Confirmation(movie_pojo movie, List<Integer> bookedSeats, int hallNumber, Ticket_Status status) {
        try {
            List<Ticket_Status> seats = movie.getHall().get(hallNumber).getSeatsList();
            for (int seat : bookedSeats) {
                seats.set(seat - 1, status);
            }
            repo.save(movie);
        } catch (Exception e) {
            log.error("Error updating hall seats: {}", e.getMessage());
            throw new RuntimeException("Failed to update movie hall seats");
        }
    }

    public movie_reserve_pojo Set_movie_reserve_pojo_Details(String movieName, List<Integer> seats, float price, int hallNumber, Ticket_Status status) {
        movie_reserve_pojo reservation = new movie_reserve_pojo();
        reservation.setMovie(movieName);
        reservation.setReserved_seats(seats);
        reservation.setTotal_Price(price);
        reservation.setHall_Number(hallNumber);
        reservation.setReservedAt(LocalDateTime.now());
        reservation.setStatus(status);
        return reservation;
    }

    public user_pojo toUser(user_DTO userDto) {
        try {
            return mapper.map(userDto, user_pojo.class);
        } catch (Exception e) {
            log.error("Error converting user_DTO to user_pojo: {}", e.getMessage());
            return null;
        }
    }

    public void Update_User_Movie(user_pojo user) {
        try {
            userClient.update_User_Movies(toUserDto(user));
        } catch (Exception e) {
            log.error("Error updating user movies: {}", e.getMessage());
            throw new RuntimeException("Failed to update user movie data");
        }
    }

    public user_DTO toUserDto(user_pojo userPojo) {
        try {
            return mapper.map(userPojo, user_DTO.class);
        } catch (Exception e) {
            log.error("Error converting user_pojo to user_DTO: {}", e.getMessage());
            return null;
        }
    }
}
