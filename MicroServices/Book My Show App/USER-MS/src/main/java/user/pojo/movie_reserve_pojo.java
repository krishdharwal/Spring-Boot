package user.pojo;


import lombok.Builder;
import lombok.Data;
import nonapi.io.github.classgraph.json.Id;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class movie_reserve_pojo {
    @Id
    ObjectId id;
    String movie;
    float Total_Price;
    int Hall_Number;
    List<Integer> reserved_seats = new ArrayList<>();
}
