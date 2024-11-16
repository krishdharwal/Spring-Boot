package user.Dto;


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
    List<Integer> reserved_seets = new ArrayList<>();
}
