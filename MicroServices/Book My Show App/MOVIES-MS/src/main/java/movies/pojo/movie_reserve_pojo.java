package movies.pojo;

import lombok.Data;
import lombok.NoArgsConstructor;
import movies.Enum.Ticket_Status;
import nonapi.io.github.classgraph.json.Id;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Document("Reserved_Movies")
public class movie_reserve_pojo {
    @Id
    public ObjectId id;
    String movie;
    float Total_Price;
    int Hall_Number;
    LocalDateTime reservedAt;
    Ticket_Status status;
    List<Integer> reserved_seats = new ArrayList<>();
}
