package user.pojo;


import lombok.Builder;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import user.Enum.Ticket_Status;

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
