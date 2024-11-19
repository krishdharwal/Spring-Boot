package movies.Dto;


import lombok.Data;
import lombok.NoArgsConstructor;
import movies.Enum.Ticket_Status;
import nonapi.io.github.classgraph.json.Id;
import org.bson.types.ObjectId;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class movie_reserve_dto {
    ObjectId id;
    String movie;
    float Total_Price;
    int Hall_Number;
    LocalDateTime reservedAt;
    Ticket_Status status;
    List<Integer> reserved_seats = new ArrayList<>();
}
