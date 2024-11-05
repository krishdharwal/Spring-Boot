package movies.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import movies.Dto.movie_DTO;
import movies.Dto.updateSeat_DTO;
import movies.clients.screen_client;
import movies.pojo.movie_pojo;
import movies.repo.movie_repo;
import org.bson.types.ObjectId;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import screen.pojo.screen_pojo;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.SocketHandler;

@Service
public class movie_service {

    @Autowired
    private movie_repo repo;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private screen_client screenClient;

    @Autowired
    private Movie_Query movieQuery;

    public void save(movie_DTO movieData) {
        try{
            repo.save(toMovie(movieData));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // Movie Search Algo
    public void Search_Movie(String movieName) throws JsonProcessingException {
        Scanner in = new Scanner(System.in);

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
        in.close();
    }


    // Booking algo
    @Transactional
    public void BookTicket(String mail_address, movie_pojo moviePojo) throws JsonProcessingException {
        Scanner in = new Scanner(System.in);
        float movie_price = moviePojo.getPrice();

        // firstly show all the cinema halls
        System.out.println("-- CHOOSE CINEMA HALL & AND ENTER THE HALL NUMBER --");

        for (screen_pojo screenPojo : moviePojo.getHall()) {

            System.out.println("1. -> " + moviePojo.getHall());

        }
        int Hall_number = in.nextInt();

        List<Integer> bookedSeats = new ArrayList<>();
        List<Boolean> SeatBoolList;

        if (Hall_number > 0  && Hall_number < moviePojo.getHall().size()) {
            SeatBoolList = moviePojo.getHall().get(Hall_number - 1).getSeatsList();
            bookedSeats = screenClient.Book_Seats(SeatBoolList);
        }else {
            System.out.println("-- ENTER VALID HALL NUMBER --");
        }

        // Next Step What user want ----->
        assert !bookedSeats.isEmpty();
        float total_price = bookedSeats.size() * movie_price;
        System.out.println("YOUR TOTAL PRICE FOR [ " + bookedSeats.size()  + " ] IS -> " + total_price);
        System.out.println("   ENTER 1 TO CONFIRM" +
                           "\n ENTER 2 TO RESERVE FOR 5 MINUTE" +
                           "\n ENTER 3 TO CANCLE ");

        // confirmation , reservation , canclation
        int what = in.nextInt();
        if (what == 1){
            // confirm algo -> payment gateway , user auth , book seatsList and save it , send mail , update seats in db

            // authentication

            // update the bool list seats
            ObjectId id = moviePojo.getHall().get(Hall_number).getId();
            updateSeat_DTO updateSeatDto = new updateSeat_DTO();
            updateSeatDto.setHall_id(id);
            updateSeatDto.setBooked_seets(bookedSeats);
            SeatBoolList = screenClient.updateSeats(updateSeatDto);
            List<Boolean> updateSeats = moviePojo.getHall().get(Hall_number).getSeatsList();
            updateSeats = SeatBoolList;
            repo.save(moviePojo);

            // At last send mail_address
            ObjectMapper objectMapper = new ObjectMapper();
            Send_Mail( mail_address,
                    "-- TICKET BOOKING CONFIRMED --",
                    "-- Hooray! your ticket is booked " +
                            "\n Total tickets -> " + bookedSeats.size() +
                            "\n And Total price is -> " + total_price +
                            "\n" + objectMapper.writeValueAsString(moviePojo)
            );

        }else if (what == 2){
            // reserve algo -> send mail_address about the reservstion for 5 minutes

            // reserve the ticket

            //  send mail of reservation
            ObjectMapper objectMapper = new ObjectMapper();
            Send_Mail( mail_address,
                    "-- TICKET RESERVATION --",
                    "-- YOUR TICKET HAVE BEEN SUCCESSFULLY RESERVED FOR 5 MINUTES --" +
                            "\n Total tickets -> " + bookedSeats.size() +
                            "\n And Total price is -> " + total_price +
                            "\n" + objectMapper.writeValueAsString(moviePojo)
            );

        }else {
            if (what == 3){
                // cancle algo
                System.out.println("-- CANCLATION ACCEPTED --");
            }
            else {
                System.out.println("-- ENTER A CORRECT NUMBER --");
            }
        }


        in.close();
    }


    // mail
    public void Send_Mail(String to , String subject , String Message){
        SimpleMailMessage message  = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(Message);
        mailSender.send(message);
    }


    // mapper
    private movie_pojo toMovie(movie_DTO movieDto){
        assert movieDto != null;
        return mapper.map(movieDto,movie_pojo.class);
    }




//    public static void main(String[] args) {
//        Scanner in = new Scanner(System.in);
//        List<Integer> seatNo = new ArrayList<>();
//        int sno = 0;
//        while (!(sno < 0)) {
//            sno = in.nextInt();
//            seatNo.add(sno);
//        }
//        System.out.println(seatNo);
//    }

}
