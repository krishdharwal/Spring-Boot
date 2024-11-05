package screen.service;

import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import screen.pojo.screen_pojo;
import screen.repo.screen_repo;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Service
@Slf4j
public class screen_service {

    @Autowired
    private screen_repo repo;

    public List<Integer> Book_Seats(List<Boolean> Current_Seats){
        System.out.println("<--- ENTER SEAT'S NUMBER TO BOOK & ENTER -1 TO CONFIRM --->");
        Scanner in = new Scanner(System.in);

        List<Integer> seatNo_List = new ArrayList<>(Current_Seats.size());
        int sno = 0;

        // display current seets
        System.out.println(Current_Seats);

        while (!(sno < 0)) {
            sno = in.nextInt();
            if (Current_Seats.get(sno)) {
                seatNo_List.add(sno);
            } else if (sno >= Current_Seats.size()) {
                System.out.println("<--- PLEASE ENTER A VALID SEAT NUMBER --->");
            }else {
                System.out.println("<--- SORRY THIS SEAT IS RESERVED --->");
            }
        }
        return seatNo_List;
    }



    //  update_Reserved_seets_of_hall algo
    public List<Boolean> update_Reserved_seets_of_hall(ObjectId hall_Id, List<Integer> bookedSeets) {
        try{
            screen_pojo hall_from_db = repo.findById(hall_Id).orElse(null);
            assert hall_from_db != null;

            for(int i : bookedSeets){
                hall_from_db.getSeatsList().set(i , false);
            }
            repo.save(hall_from_db);
            return hall_from_db.getSeatsList();
        } catch (Exception e) {
            log.error(" -- error in update_Reserved_seets_of_hall in screen service --");
            return null;
        }
    }

}
