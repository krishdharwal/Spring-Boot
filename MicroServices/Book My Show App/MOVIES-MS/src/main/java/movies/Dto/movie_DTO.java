package movies.Dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import movies.pojo.screen_pojo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
public class movie_DTO {
    ObjectId  id;
    String    name;
    String    description;
    String    language;
    String    genre;
    Date      releaseDate;
    float     duration;
    int       rating;
    float     price;
    List<screen_pojo> hall = new ArrayList<>();
}
