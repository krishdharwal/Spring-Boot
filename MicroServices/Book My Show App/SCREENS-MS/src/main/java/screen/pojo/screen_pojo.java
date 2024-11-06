package screen.pojo;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

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
    public List<Boolean> seatsList = new ArrayList<>(Collections.nCopies(TotalSeats,false));
    // screen type , screen ratio, timing

}
