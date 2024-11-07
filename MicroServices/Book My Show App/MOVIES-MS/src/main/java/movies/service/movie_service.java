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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

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

    Scanner in = new Scanner(System.in);

    public void save(movie_DTO movieData) {
        try{
            repo.save(toMovie(movieData));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // Movie Search Algo
    public void Search_Movie(String movieName) throws JsonProcessingException {

        movie_pojo movie = movieQuery.find_Movie_By_Name(movieName);
        assert movie != null;

        System.out.println(movie);
        System.out.println("-- DO YOU WANT TO BOOK THIS MOVIE --");
        System.out.println("ENTER 1 TO WATCH THIS MOVIE NEARBY " +
                "\n ENTER 2 TO CANCLE");

        int next = in.nextInt();
        if (next == 1){
            BookTicket("knightkrishcoc3@gmail.com",movie);
        }else if (next == 2){
            System.out.println("-- THANKS VISIT AGAIN --");
        }else {
            System.out.println("-- ENTER VALID NUMBER --");
        }
    }

    // Booking algo
    public void BookTicket(String mail_address, movie_pojo moviePojo) throws JsonProcessingException {
        float movie_price = moviePojo.getPrice();

        // firstly show all the cinema halls
        System.out.println("-- CHOOSE CINEMA HALL & AND ENTER THE HALL NUMBER --");


        for (screen_pojo screenPojo : moviePojo.getHall()) {
            System.out.println( " 1 -> " + moviePojo.getHall());
        }

        int Hall_number = in.nextInt();

        List<Integer> bookedSeats = new ArrayList<>();
        List<Boolean> SeatBoolList;

        if (Hall_number > 0 && Hall_number <= moviePojo.getHall().size()) {
            SeatBoolList = moviePojo.getHall().get(Hall_number - 1).getSeatsList();
            bookedSeats = Book_Seats(SeatBoolList);
        } else {
            System.out.println("-- ENTER VALID HALL NUMBER --");
        }

        // Next Step What user want ----->
       if(!bookedSeats.isEmpty()){
       Next_Procedure(bookedSeats, movie_price , moviePojo, Hall_number, mail_address, in);
    }
    }

//    // get booked seat list
//    public List<Integer> Get_Booked_seets(bookSeat_bool_list__DTO bookSeatBoolList__dto){
//        List<Integer> bookedSeats = screenClient.Book_Seats(bookSeatBoolList__dto);
//        return bookedSeats;
//    }

    public List<Integer> Book_Seats(List<Boolean> Current_Seats) {
        System.out.println("<--- ENTER SEAT'S NUMBER TO BOOK & ENTER -1 TO CONFIRM --->");

        List<Integer> seatNo_List = new ArrayList<>();

        // Display current seats
        System.out.println("Current Seats Status: " + Current_Seats);

        while (true) {
            int sno = in.nextInt();

            if (sno == -1) {
                break; // Confirm booking and exit loop
            }

            if (sno >= 0 && sno < Current_Seats.size()) { // Check for valid seat number
                if (!Current_Seats.get(sno)) {
                    seatNo_List.add(sno);
                    Current_Seats.set(sno, true); // Mark seat as booked (true)
                    System.out.println("-- Seat added -> " + sno);
                } else {
                    System.out.println("<--- SORRY, THIS SEAT IS NOT AVAILABLE --->");
                }
            } else {
                System.out.println("<--- INVALID SEAT NUMBER, PLEASE TRY AGAIN --->");
            }
        }
        return seatNo_List;
    }

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

    public void Next_Procedure(List<Integer> bookedSeats, float movie_price, movie_pojo moviePojo, int Hall_number, String mail_address, Scanner in ) throws JsonProcessingException {
    // confirmation , reservation , canclation

    float total_price = bookedSeats.size() * movie_price;
    System.out.println("YOUR TOTAL PRICE FOR [ " + bookedSeats.size() + " ] IS -> " + total_price);
    System.out.println("   ENTER 1 TO CONFIRM" +
            "\n ENTER 2 TO RESERVE FOR 5 MINUTE" +
            "\n ENTER 3 TO CANCLE ");

    int Next_Number = in.nextInt();
    switch (Next_Number){
        case 1: Book(mail_address,bookedSeats,total_price,moviePojo, Hall_number);
            break;

        case 2: Reserve(mail_address,bookedSeats,total_price,moviePojo);
            break;

        case 3: Cancle();
            break;

        default:
            System.out.println(" enter a valid number ");
    }
// scanner close
//    in.close();
}

    public void Book(String mail_address, List<Integer> bookedSeats, float total_price, movie_pojo moviePojo, int hall_number) throws JsonProcessingException {
    // confirm algo -> payment gateway , user auth , book seatsList and save it , send mail , update seats in db

    // authentication

    // updation part -> in movie and in screens
    update_Movie_Hall_Seets(moviePojo, bookedSeats ,hall_number);

    // At last send mail_address
    ObjectMapper objectMapper = new ObjectMapper();
    Send_Mail(mail_address,
            "-- TICKET BOOKING CONFIRMED --",
            "-- Hooray! your ticket is booked " +
                    "\n Total tickets -> " + bookedSeats.size() +
                    "\n And Total price is -> " + total_price +
                    "\n" + objectMapper.writeValueAsString(moviePojo)
    );
    System.out.println(" -- Seat Booked -- ");
}

// update the movie Hall seats
    private void update_Movie_Hall_Seets(movie_pojo moviePojo,List<Integer> Booked_Hall_seats, int Hall_number) {
        try{
            List<Boolean>  movie_Hall_seat_DB = moviePojo.getHall().get(Hall_number).getSeatsList();

            for (int i = 0; i < Booked_Hall_seats.size() ; i++) {
                int idx = Booked_Hall_seats.get(i);
                movie_Hall_seat_DB.set(idx , true);
            }
//            int size = moviePojo.getHall().get(Hall_number).getTotalSeats();
//            moviePojo.getHall().get(Hall_number).setTotalSeats(size - Booked_Hall_seats.size());
            repo.save(moviePojo);
        } catch (Exception e) {
            log.error(" -- errror in update_Movie_Hall_Seets -- ");
        }
}

    public void Reserve(String mail_address, List<Integer> bookedSeats, float total_price, movie_pojo moviePojo) throws JsonProcessingException {
    // reserve algo -> send mail_address about the reservstion for 5 minutes

    // reserve the ticket

    //  send mail of reservation
    ObjectMapper objectMapper = new ObjectMapper();
    Send_Mail(mail_address,
            "-- TICKET RESERVATION --",
            "-- YOUR TICKET HAVE BEEN SUCCESSFULLY RESERVED FOR 5 MINUTES --" +
                    "\n Total tickets -> " + bookedSeats.size() +
                    "\n And Total price is -> " + total_price +
                    "\n" + objectMapper.writeValueAsString(moviePojo)
    );

    System.out.println(" -- Seat Reserved -- ");
}

    public void Cancle(){
    System.out.println("-- CANCLATION ACCEPTED --");
}


    // mail
    public void Send_Mail(String to , String subject , String Message){
    SimpleMailMessage message  = new SimpleMailMessage();
    message.setTo(to);
    message.setSubject(subject);
    message.setText(Message);
    mailSender.send(message);
}

}
