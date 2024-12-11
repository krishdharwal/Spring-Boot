package movies.pojo;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import movies.pojo.screen_pojo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Document("Movies")
@NoArgsConstructor
public class movie_pojo {
    @Id
    ObjectId id;
    @NonNull
    String name;
    String description;
    String language;
    String genre;
    Date releaseDate;
    float duration;
    int rating;
    float price;
    List<screen_pojo> hall = new ArrayList<>();
}


