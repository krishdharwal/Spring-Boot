package movies.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import movies.Dto.movie_DTO;
import movies.clients.screen_client;
import movies.pojo.movie_pojo;
import movies.repo.movie_repo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import screen.Dto.screen_DTO;
import screen.pojo.screen_pojo;

import java.awt.image.AreaAveragingScaleFilter;
import java.util.*;

@Service
@Slf4j
public class movie_service {

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

    // scanner
    Scanner in = new Scanner(System.in);

    // save movie
    public void save(movie_DTO movieData) {
        try{
            repo.save(toMovie(movieData));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // set_hall_in_movie
    public void set_hall_in_movie(screen_DTO screen, String movieName) {
        try {
            assert screen != null;
            screen.setSeatsList(Collections.nCopies(screen.getTotalSeats(),false));
            movie_pojo movie = movieQuery.find_Movie_By_Name(movieName);
            assert movie != null;
            screenClient.Save_Hall(screen);
            movie.getHall().add(toScreen(screen));
            repo.save(movie);
        } catch (Exception e) {
            log.error(" -- error in set_hall_in_movie -- ;");
        }
    }

    // mapper
    private movie_pojo toMovie(movie_DTO movieDto){
        assert movieDto != null;
        return mapper.map(movieDto,movie_pojo.class);
    }

    private screen_pojo toScreen(screen_DTO screenDto){
        assert screenDto != null;
        return mapper.map(screenDto,screen_pojo.class);
    }

    // print the Movie Details
    public String Print_Movie_Details(movie_pojo movie){
       return
                "MOVIE NAME : " + movie.getName() +
                        "\n LANGUAGE : " +movie.getLanguage() +
                        "\n GENERA : " + movie.getGenre() +
                        "\n DURATION : " + movie.getDuration() +
                        "\n RATING : " + movie.getRating() +
                        "\n RELEASE DATE : " + movie.getReleaseDate()+
                        "\n DESCRIPTION : " + movie.getDescription() +
                        "\n PRICE OF TICKET : " + movie.getPrice();

    }

    // Movie Search Algo
    public void Search_Movie(String movieName) throws JsonProcessingException {

        movie_pojo movie = movieQuery.find_Movie_By_Name(movieName);
        assert movie != null;

        System.out.println( Print_Movie_Details(movie) );
        System.out.println("-- DO YOU WANT TO BOOK THIS MOVIE --");
        System.out.println("ENTER 1 TO WATCH THIS MOVIE NEARBY " +
                "\n ENTER 2 TO CANCLE");

        int next = in.nextInt();
        if (next == 1){

            // if user enter one do user auth and get his place info
            show_movie_halls("knightkrishcoc3@gmail.com",movie);

        }else if (next == 2){
            System.out.println("-- THANKS VISIT AGAIN --");
        }else {
            System.out.println("-- ENTER VALID NUMBER --");
        }
    }

    // Booking algo
    public void show_movie_halls(String mail_address, movie_pojo moviePojo) throws JsonProcessingException {
        float movie_price = moviePojo.getPrice();

        // firstly show all the cinema halls
        System.out.println("-- CHOOSE CINEMA HALL & AND ENTER THE HALL NUMBER --");

        int i = 1;
        for (screen_pojo screenPojo : moviePojo.getHall()) {
            System.out.println( i + " -> " + moviePojo.getHall());
            i++;
        }

        int Hall_number = in.nextInt();

        List<Integer> booked_Seats_Integer_list = new ArrayList<>();
        List<Boolean> booked_Seats_Bool_list;

        if (Hall_number > 0 && Hall_number <= moviePojo.getHall().size()) {

            booked_Seats_Bool_list = moviePojo.getHall().get(Hall_number - 1).getSeatsList();

            // Book the seats
            booked_Seats_Integer_list = Book_Seats_of_the_hall(booked_Seats_Bool_list);

            // Next Step What user want confirmation , reservation , canclation ----->
            assert !booked_Seats_Integer_list.isEmpty();
            Select_for_Confirm_Reserve_Cancle(booked_Seats_Integer_list, movie_price , moviePojo, Hall_number, mail_address, in);

        } else {
            System.out.println("-- ENTER VALID HALL NUMBER --");
        }

    }



    public List<Integer> Book_Seats_of_the_hall(List<Boolean> Current_Seats_Bool_List) {
        System.out.println("<--- ENTER SEAT'S NUMBER TO BOOK & ENTER -1 TO CONFIRM --->" +
                " \n False -> Un-Booked | True -> Booked ");

        List<Integer> Selected_seats_by_user_Integer_list = new ArrayList<>();

        // Display current seats
        System.out.println("Current Seats Status: " + Current_Seats_Bool_List);

        while (true) {
            int sno = in.nextInt();

            if (sno == -1) {
                break; // Confirm booking and exit loop
            }

            if (sno > 0 && sno <= Current_Seats_Bool_List.size()) {
                // Check for valid seat number
                int idx = sno - 1;
                if (!Current_Seats_Bool_List.get(idx)) {

                    Selected_seats_by_user_Integer_list.add(idx);
                    Current_Seats_Bool_List.set(idx, true); // Mark seat as booked (true)
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

    public void Select_for_Confirm_Reserve_Cancle(List<Integer> booked_Seats_Integer_List, float movie_price, movie_pojo moviePojo, int Hall_number, String mail_address, Scanner in ) throws JsonProcessingException {
    // confirmation , reservation , canclation

    float total_price = booked_Seats_Integer_List.size() * movie_price;
    System.out.println("YOUR TOTAL PRICE FOR [ " + booked_Seats_Integer_List.size() + " Tickets ] IS -> " + total_price);
    System.out.println("   ENTER 1 TO CONFIRM" +
            "\n ENTER 2 TO RESERVE FOR 5 MINUTE" +
            "\n ENTER 3 TO CANCLE ");

    int Next_Number = in.nextInt();
    switch (Next_Number){
        case 1: Booking_confirmation(mail_address,booked_Seats_Integer_List,total_price,moviePojo, Hall_number);
            break;

        case 2: Reserve_Ticket(mail_address,booked_Seats_Integer_List,total_price,moviePojo);
            break;

        case 3: Cancle_Ticket();
            break;

        default:
            System.out.println("  -- Enter a valid number -- ");
    }
// scanner close
//    in.close();
}

    public void Booking_confirmation(String mail_address, List<Integer> booked_Seats_Integer_List, float total_price, movie_pojo moviePojo, int hall_number) throws JsonProcessingException {
    // confirm algo -> payment gateway , user auth , book seatsList and save it , send mail , update seats in db

    // authentication

    // updation part -> in movie and not in screens (Movie Hall)
    update_Movie_Hall_Seets(moviePojo, booked_Seats_Integer_List ,hall_number - 1);

    // At last send mail_address
    Send_Mail(mail_address,
            "-- TICKET BOOKING CONFIRMED --",
            "-- Hooray! your ticket is booked " +
                    "\n Total tickets -> " + booked_Seats_Integer_List.size() +
                    "\n And Total price is -> " + total_price +
                    "\n" + Print_Movie_Details(moviePojo)
    );
    System.out.println(" -- Seat Booked -- ");
}

// update the movie Hall seats
    private void update_Movie_Hall_Seets(movie_pojo moviePojo,List<Integer> booked_Seats_Integer_List, int Hall_number) {
        try{
            List<Boolean>  movie_Hall_seat_DB = moviePojo.getHall().get(Hall_number).getSeatsList();
            for (int i = 0; i < booked_Seats_Integer_List.size() ; i++) {
                int idx = booked_Seats_Integer_List.get(i);
                movie_Hall_seat_DB.set(idx, true);
            }
            repo.save(moviePojo);
        } catch (Exception e) {
            log.error(" -- errror in update_Movie_Hall_Seets -- ");
        }
}


public void Reserve_Ticket(String mail_address, List<Integer> bookedSeats, float total_price, movie_pojo moviePojo) throws JsonProcessingException {
    // reserve algo -> send mail_address about the reservstion for 5 minutes

    // reserve the ticket

    //  send mail of reservation
    Send_Mail(mail_address,
            "-- TICKET RESERVATION --",
            "-- YOUR TICKET HAVE BEEN SUCCESSFULLY RESERVED FOR 5 MINUTES --" +
                    "\n Total tickets -> " + bookedSeats.size() +
                    "\n And Total price is -> " + total_price +
                    "\n" + Print_Movie_Details(moviePojo)
    );

    System.out.println(" -- Seat Reserved -- ");
}

public void Cancle_Ticket(){
    System.out.println("-- CANCLATION ACCEPTED --");
}

    // send  mail
    public void Send_Mail(String to , String subject , String Message){
    SimpleMailMessage message  = new SimpleMailMessage();
    message.setTo(to);
    message.setSubject(subject);
    message.setText(Message);
    mailSender.send(message);
}


// Testing
    public static void main(String[] args) {

        movie_service obj = new movie_service();
        List<Integer> booked_Seats_Integer_list = Arrays.asList(1,3);
        List<Boolean> movie_Hall_seat_DB = Arrays.asList(false, false , false);

        for (int i = 0; i < booked_Seats_Integer_list.size() ; i++) {
            int idx = booked_Seats_Integer_list.get(i);
            movie_Hall_seat_DB.set(idx , true);
        }

        System.out.println(movie_Hall_seat_DB);


    }

}
