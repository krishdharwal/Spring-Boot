package screen.pojo;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import screen.Enum.Ticket_Status;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Data
@NoArgsConstructor
@Document("Screens")
public class screen_pojo {
    @Id
    public ObjectId id;
    public   String name;
    @NonNull
    public int TotalSeats;
    public List<Ticket_Status> seatsList = new ArrayList<>(Collections.nCopies(TotalSeats,Ticket_Status.OPENED));
    // screen type , screen ratio, timing

}
