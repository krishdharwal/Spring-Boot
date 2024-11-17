package movies.pojo;


import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import nonapi.io.github.classgraph.json.Id;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class movie_reserve_pojo {
    @Id
    ObjectId id;
    String movie;
    float Total_Price;
    int Hall_Number;
    List<Integer> reserved_seets = new ArrayList<>();
}
