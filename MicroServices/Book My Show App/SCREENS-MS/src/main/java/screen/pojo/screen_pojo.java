package screen.pojo;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Document("Screens")
public class screen_pojo {
    @Id
    ObjectId id;
    String name;
    @NonNull
    Integer TotalSeats;
    List<Boolean> seatsList = new ArrayList<>(TotalSeats);

    // screen type , screen ratio, timing

}
