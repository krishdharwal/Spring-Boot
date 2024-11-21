package user.Dto;


import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import user.Enum.Ticket_Status;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class movie_reserve_dto {
    public ObjectId id;
    String movie;
    float Total_Price;
    int Hall_Number;
    LocalDateTime reservedAt;
    Ticket_Status status;
    List<Integer> reserved_seats = new ArrayList<>();
}
