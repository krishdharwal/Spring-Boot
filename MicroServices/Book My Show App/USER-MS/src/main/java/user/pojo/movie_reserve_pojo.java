package user.pojo;


import lombok.Builder;
import lombok.Data;
import movies.Enum.Ticket_Status;
import nonapi.io.github.classgraph.json.Id;
import org.bson.types.ObjectId;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
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
