package movies.Dto;


import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class movie_reserve_dto {
    ObjectId id;
    String movie;
    float Total_Price;
    int Hall_Number;
    List<Integer> reserved_seets = new ArrayList<>();
}
