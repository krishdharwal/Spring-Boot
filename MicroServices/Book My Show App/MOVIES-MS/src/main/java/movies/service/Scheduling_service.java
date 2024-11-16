package movies.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class Scheduling_service {

    @Scheduled(cron = "0 0 9 * * SUN")
    public void Start_Schedule_reservation(){

    }

    public void Reserve_Or_UnReserve(){
        if (true){
            // user book the seet , book them

        }
        else {
//            unreserve them
        }
    }

}
