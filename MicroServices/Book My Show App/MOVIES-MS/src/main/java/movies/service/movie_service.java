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

    // scanner
    Scanner in = new Scanner(System.in);

    // save movie
    public void save(movie_DTO movieData) {
        try {
            repo.save(toMovie(movieData));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // set_hall_in_movie
    public void set_hall_in_movie(screen_DTO screen, String movieName) {
        try {
            assert screen != null;
            screen.setSeatsList(Collections.nCopies(screen.getTotalSeats(), false));
            movie_pojo movie = movieQuery.find_Movie_By_Name(movieName);
            assert movie != null;
            screenClient.Save_Hall(screen);
            movie.getHall().add(toScreen(screen));
            repo.save(movie);
        } catch (Exception e) {
            log.error(" -- error in set_hall_in_movie -- ;");
        }
    }


    // print the Movie Details
    public String Print_Movie_Details(movie_pojo movie) {
        return
                "MOVIE NAME : " + movie.getName() +
                        "\n LANGUAGE : " + movie.getLanguage() +
                        "\n GENERA : " + movie.getGenre() +
                        "\n DURATION : " + movie.getDuration() +
                        "\n RATING : " + movie.getRating() +
                        "\n RELEASE DATE : " + movie.getReleaseDate() +
                        "\n DESCRIPTION : " + movie.getDescription() +
                        "\n PRICE OF TICKET : " + movie.getPrice();
    }

    // Movie Search Algo
    public void Search_Movie(String movieName, user_DTO user) throws JsonProcessingException {

        movie_pojo movie = movieQuery.find_Movie_By_Name(movieName);
        assert movie != null;

        System.out.println(Print_Movie_Details(movie));
        System.out.println("-- DO YOU WANT TO BOOK THIS MOVIE --");
        System.out.println("ENTER 1 TO WATCH THIS MOVIE NEARBY " +
                "\n ENTER 2 TO CANCLE");

        int next = in.nextInt();
        if (next == 1) {

            // if user enter one do user auth and get his place info
            show_movie_halls(movie, user);

        } else if (next == 2) {
            System.out.println("-- THANKS VISIT AGAIN --");
        } else {
            System.out.println("-- ENTER VALID NUMBER --");
        }
    }

    // Booking algo
    public void show_movie_halls(movie_pojo moviePojo, user_DTO user) throws JsonProcessingException {
        float movie_price = moviePojo.getPrice();

        // firstly show all the cinema halls
        System.out.println("-- CHOOSE CINEMA HALL & AND ENTER THE HALL NUMBER --");

        int i = 1;
        for (screen_pojo screenPojo : moviePojo.getHall()) {
            System.out.println(i + " -> " + moviePojo.getHall());
            i++;
        }

        int Hall_number = in.nextInt();

        List<Integer> booked_Seats_Integer_list = new ArrayList<>();
        List<Ticket_Status> booked_Seats_Bool_list;

        if (Hall_number > 0 && Hall_number <= moviePojo.getHall().size()) {

            booked_Seats_Bool_list = moviePojo.getHall().get(Hall_number - 1).getSeatsList();

            // Book the seats
            booked_Seats_Integer_list = Book_Seats_of_the_hall(booked_Seats_Bool_list);

            // Next Step What user want confirmation , reservation , canclation ----->
            assert !booked_Seats_Integer_list.isEmpty();
            Select_for_Confirm_Reserve_Cancle(booked_Seats_Integer_list, movie_price, moviePojo, Hall_number, in, user);

        } else {
            System.out.println("-- ENTER VALID HALL NUMBER --");
        }

    }


    public List<Integer> Book_Seats_of_the_hall(List<Ticket_Status> Current_Seats) {
        System.out.println("<--- ENTER SEAT'S NUMBER TO BOOK & ENTER -1 TO CONFIRM --->" +
                " \n False -> Un-Booked | True -> Booked ");

        List<Integer> Selected_seats_by_user_Integer_list = new ArrayList<>();

        // Display current seats
        System.out.println("Current Seats Status: " + Current_Seats);

        while (true) {
            int sno = in.nextInt();

            if (sno == -1) {
                break; // Confirm booking and exit loop
            }

            if (sno > 0 && sno <= Current_Seats.size()) {
                // Check for valid seat number
                int idx = sno - 1;
                if (Current_Seats.get(idx).equals(Ticket_Status.OPENED)) {

                    Selected_seats_by_user_Integer_list.add(idx);
                    Current_Seats.set(idx, Ticket_Status.RESERVED); // Mark seat as booked (true)
                    System.out.println("-- Seat added -> " + sno);

                } else {
                    System.out.println("<--- SORRY, THIS SEAT IS NOT AVAILABLE --->");
                }
            } else {
                System.out.println("<--- INVALID SEAT NUMBER, PLEASE TRY AGAIN --->");
            }
        }
        return Selected_seats_by_user_Integer_list;
    }

    public void Select_for_Confirm_Reserve_Cancle(List<Integer> booked_Seats_Integer_List, float movie_price, movie_pojo moviePojo, int Hall_number, Scanner in, user_DTO userDTO) throws JsonProcessingException {
        // confirmation , reservation , canclation

        float total_price = booked_Seats_Integer_List.size() * movie_price;
        System.out.println("YOUR TOTAL PRICE FOR [ " + booked_Seats_Integer_List.size() + " Tickets ] IS -> " + total_price);
        System.out.println("   ENTER 1 TO CONFIRM" +
                "\n ENTER 2 TO RESERVE FOR 5 MINUTE" +
                "\n ENTER 3 TO CANCLE ");

        int Next_Number = in.nextInt();

        // find the user and get him
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        user_pojo user = toUser(userDTO);
        assert user != null;

        switch (Next_Number) {
            case 1:
                Booking_confirmation(user, booked_Seats_Integer_List, total_price, moviePojo, Hall_number);
                break;

            case 2:
                Reserve_Ticket(user, booked_Seats_Integer_List, total_price, moviePojo, Hall_number);
                break;

            case 3:
                Cancle_Ticket();
                break;

            default:
                System.out.println("  -- Enter a valid number -- ");
        }
    }

    public void Booking_confirmation(user_pojo user, List<Integer> bookedSeats, float total_price, movie_pojo moviePojo, int hall_number) throws JsonProcessingException {
        // confirm algo -> payment gateway , user auth , book seatsList and save it , send mail , update_reserved_seats seats in db

        // authentication

        // updation part -> in movie and not in screens (Movie Hall)
        update_Movie_Hall_Seats_After_Confirmation(moviePojo, bookedSeats, hall_number - 1, Ticket_Status.BOOKED);

        // save the booked movie in the user
        movie_reserve_pojo movieReservePojo =  Set_movie_reserve_pojo_Details(
                moviePojo.getName(),bookedSeats,total_price,hall_number,Ticket_Status.BOOKED
        );

        user.getReservedMovies().add(movieReservePojo);
        Update_User_Movie(user);

        // At last send mail_address
        Send_Mail(user.getMail(),
                "-- TICKET BOOKING CONFIRMED --",
                "-- Hooray! your ticket is booked " +
                        "\n Total tickets -> " + bookedSeats.size() +
                        "\n And Total price is -> " + total_price +
                        "\n" + Print_Movie_Details(moviePojo)
        );
        System.out.println(" -- Seat Booked -- ");
    }

    // update_reserved_seats the movie Hall seats
    private void update_Movie_Hall_Seats_After_Confirmation(movie_pojo moviePojo, List<Integer> booked_Seats_Integer_List, int Hall_number, Ticket_Status status) {
        try {
            // updation part -> in movie and not in screens (Movie Hall)
            List<Ticket_Status> movie_Hall_seat_DB = moviePojo.getHall().get(Hall_number).getSeatsList();
            for (int i = 0; i < booked_Seats_Integer_List.size(); i++) {
                int idx = booked_Seats_Integer_List.get(i);
                movie_Hall_seat_DB.set(idx, status);
            }
            repo.save(moviePojo);


        } catch (Exception e) {
            log.error(" -- error in update_Movie_Hall_Seats_After_Confirmation -- ");
        }
    }

    public void Reserve_Ticket(user_pojo user, List<Integer> bookedSeats, float total_price, movie_pojo moviePojo, int hall_number) throws JsonProcessingException {
        // reserve algo -> send mail_address about the reservstion for 5 minutes

        // set the user reserved seats for future
        movie_reserve_pojo movieReservePojo =   Set_movie_reserve_pojo_Details(
                moviePojo.getName(),bookedSeats,total_price,hall_number,Ticket_Status.RESERVED
        );
        // reserve the ticket
        // user things
        user.getReservedMovies().add(movieReservePojo);
        // send user to being updated
        // send this id to book the reserved seats
        ObjectId Ticket_Id = schedulingService.Schedule_Reserve_Ticket(toUserDto(user) , movieReservePojo);

        //  send mail of reservation
        Send_Mail(user.getMail(),
                "-- TICKET RESERVATION --",
                "-- YOUR TICKET HAVE BEEN SUCCESSFULLY RESERVED FOR 5 MINUTES --" +
                        "\n Ticket Id -> " + Ticket_Id +
                        "\n Total tickets -> " + bookedSeats.size() +
                        "\n And Total price is -> " + total_price +
                        "\n" + Print_Movie_Details(moviePojo)
        );
        System.out.println(" -- Seat Reserved -- ");
    }

    public void Cancle_Ticket() {
        System.out.println("-- CANCELATION ACCEPTED --");
    }

    // send  mail
    public void Send_Mail(String to, String subject, String Message) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(Message);
        mailSender.send(message);
    }

    // mapper
    private movie_pojo toMovie(movie_DTO movieDto) {
        assert movieDto != null;
        return mapper.map(movieDto, movie_pojo.class);
    }

    private screen_pojo toScreen(screen_DTO screenDto) {
        assert screenDto != null;
        return mapper.map(screenDto, screen_pojo.class);
    }

    public user_pojo toUser(user_DTO userDto) {
        try {
            assert userDto != null;
            return mapper.map(userDto, user_pojo.class);
        } catch (Exception e) {
            log.error(" -- error in toUser in service --");
            return null;
        }
    }

    public user_DTO toUserDto(user_pojo userPojo) {
        try {
            assert userPojo != null;
            return mapper.map(userPojo, user_DTO.class);
        } catch (Exception e) {
            log.error(" -- error in toUser_Dto in service --");
            return null;
        }
    }

    // Book the seats that are reserved
    public void Book_seats_that_are_reserved(user_DTO userPojo, movie_reserve_pojo movieReservePojo) {
        try {
            movie_pojo moviePojo = movieQuery.find_Movie_By_Name(movieReservePojo.getMovie());
            assert moviePojo != null;
            Booking_confirmation(toUser(userPojo), movieReservePojo.getReserved_seats(), movieReservePojo.getTotal_Price(), moviePojo, movieReservePojo.getHall_Number());
        } catch (Exception e) {
            log.error(" -- error in Book_seats_that_are_reserved in service --");
        }
    }

    public void Update_User_Movie(user_pojo user) {
        try {
            userClient.update_User_Movies(toUserDto(user));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public ObjectId Update_User_Reserved_Movie(user_pojo user) {
        try {
            return userClient.update_reserved_seats(toUserDto(user));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public movie_reserve_pojo Set_movie_reserve_pojo_Details(
            String movie_name, List<Integer> bookedSeats , float total_price, int hall_number, Ticket_Status status
    ){
        movie_reserve_pojo movieReservePojo = new movie_reserve_pojo();

        movieReservePojo.setMovie(movie_name);
        movieReservePojo.setReserved_seats(bookedSeats);
        movieReservePojo.setTotal_Price(total_price);
        movieReservePojo.setHall_Number(hall_number);
        movieReservePojo.setReservedAt(LocalDateTime.now());
        movieReservePojo.setStatus(status);

        return movieReservePojo;
    }


    public void Delete_Booked_ticket(movie_reserve_dto movieReserveDto) {
        try {
            movie_pojo movie = movieQuery.find_Movie_By_Name(movieReserveDto.getMovie());
            assert movie != null;
            screen_pojo screen = movie.getHall().get(movieReserveDto.getHall_Number());
            update_Movie_Hall_Seats_After_Confirmation(movie, movieReserveDto.getReserved_seats(), movieReserveDto.getHall_Number() - 1, Ticket_Status.OPENED);

        } catch (Exception e) {
            log.error(" -- error in Delete_Booked_ticket in movie service -- ");
        }
    }
}