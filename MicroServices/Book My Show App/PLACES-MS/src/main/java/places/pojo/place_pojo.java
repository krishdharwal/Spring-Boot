package places.pojo;

import cinema.pojo.cinema_pojo;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class place_pojo {
    @Id
    ObjectId id;
    String place;
    List<cinema_pojo> TotalCinemas = new ArrayList<>();
}
