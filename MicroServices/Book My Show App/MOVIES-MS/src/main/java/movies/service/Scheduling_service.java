package movies.service;

import lombok.extern.slf4j.Slf4j;
import movies.Dto.user_DTO;
import movies.Enum.Ticket_Status;
import movies.clients.user_client;
import movies.pojo.movie_reserve_pojo;
import movies.pojo.user_pojo;
import movies.repo.schedule_repo;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Service;

import java.util.concurrent.ScheduledFuture;

import static java.time.ZoneId.systemDefault;

@Slf4j
@Service
public class Scheduling_service {

    @Autowired
    private ThreadPoolTaskScheduler taskScheduler;
    //     private final ThreadPoolTaskScheduler taskScheduler;

    @Autowired
    private schedule_repo scheduleRepo;

    @Autowired
    private user_client userClient;


    @Scheduled(cron = "0 0 9 * * SUN")
    public void Start_Schedule_reservation(){

    }

    public ObjectId Schedule_Reserve_Ticket(user_DTO user, movie_reserve_pojo reservation){
        try {
            // save the reservation
            reservation = scheduleRepo.save(reservation);

            // Schedule a task to auto-cancel after 5 minutes
            movie_reserve_pojo finalReservation = reservation;
            ScheduledFuture<?> future = taskScheduler.schedule(() -> autoCancelReservation(finalReservation.getId()),
                    reservation.getReservedAt().plusMinutes(5).atZone(systemDefault()).toInstant());

            // update the user
            user.getReservedMovies().add(reservation);
             userClient.update_reserved_seats(user);
             return reservation.getId();

        } catch (Exception e) {
            log.error(" -- error in Schedule_Reserve_Ticket in scheduling service -- ");
            return null;
        }
    }

    private void autoCancelReservation(ObjectId id) {
        // Cancel the  reserve seats
        movie_reserve_pojo reserved_Movie = scheduleRepo.findById(id).orElse(null);
        if (reserved_Movie != null && reserved_Movie.getStatus().equals(Ticket_Status.RESERVED)){
            // change the status from Reserved to Cancel
            reserved_Movie.setStatus(Ticket_Status.CANCELED);
            scheduleRepo.save(reserved_Movie);
            System.out.println("-- Reservation auto-cancelled due to timeout. --");
        }
    }


}
