package movies.Dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import movies.Enum.Ticket_Status;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;


@Data
@NoArgsConstructor
public class screen_DTO {
    public ObjectId id;
    public String name;
    public int TotalSeats;
    public List<Ticket_Status> seatsList = new ArrayList<>(Collections.nCopies(TotalSeats,Ticket_Status.OPENED));
}
