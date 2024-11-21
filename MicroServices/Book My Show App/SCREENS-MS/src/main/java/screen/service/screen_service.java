package screen.service;

import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import screen.Dto.screen_DTO;
import screen.Enum.Ticket_Status;
import screen.pojo.screen_pojo;
import screen.repo.screen_repo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

@Service
@Slf4j
public class screen_service {

    @Autowired
    private screen_repo repo;

    @Autowired
    private ModelMapper mapper;

    public static List<Integer> Book_Seats(List<Boolean> Current_Seats) {
        System.out.println("<--- ENTER SEAT'S NUMBER TO BOOK & ENTER -1 TO CONFIRM --->");
        Scanner in = new Scanner(System.in);

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

        in.close();
        return seatNo_List;
    }

    //  update_Reserved_seets_of_hall algo
    public List<Ticket_Status> update_Reserved_seets_of_hall(ObjectId hall_Id, List<Integer> bookedSeets) {
        try{
            screen_pojo hall_from_db = repo.findById(hall_Id).orElse(null);
            assert hall_from_db != null;

            for(int i : bookedSeets){
                hall_from_db.getSeatsList().set(i , Ticket_Status.BOOKED);
            }
            repo.save(hall_from_db);
            return hall_from_db.getSeatsList();
        } catch (Exception e) {
            log.error(" -- error in update_Reserved_seets_of_hall in screen service --");
            return null;
        }
    }

    public void save(screen_DTO screenDto) {
        try{
            repo.save(toScreen(screenDto));
        }catch (Exception e){
            log.error(" -- error in save screen in  screen service --  ",e);
        }
    }

    private screen_pojo toScreen(screen_DTO screenDto){
        assert screenDto != null;
        return mapper.map(screenDto,screen_pojo.class);
    }


    public static void main(String[] args) {
      List<Boolean> bool = Arrays.asList(true, false, false, false);
        System.out.println(Book_Seats(bool));
    }


}
