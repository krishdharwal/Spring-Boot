package movies.pojo;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class Current_Movies_pojo {
    @Id
    ObjectId id;
    movie_pojo movie;
    List<Integer> BookedSeats = new ArrayList<>();
}
