package user.pojo;


import lombok.Builder;
import lombok.Data;
import nonapi.io.github.classgraph.json.Id;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document("User_Reserved_seets")
@Data
@Builder
public class movie_reserve_pojo {
    @Id
    ObjectId id;
    String movie;
    List<Integer> reserved_seets = new ArrayList<>();
}
