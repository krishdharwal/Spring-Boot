package movies.Dto;


import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;


@Data
@NoArgsConstructor
public class updateSeat_DTO {
    ObjectId hall_id;
    List<Integer> booked_seets = new ArrayList<>();
}
