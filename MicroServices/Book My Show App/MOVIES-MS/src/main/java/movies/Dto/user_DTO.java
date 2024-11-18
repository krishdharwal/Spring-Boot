package movies.Dto;

import lombok.Builder;
import lombok.Data;
import movies.Enum.Roles_enum;
import movies.pojo.movie_reserve_pojo;
import org.bson.types.ObjectId;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class user_DTO {
    ObjectId id;
    String name;
    String password;
    String mail;
    String place;
    Roles_enum Role;
    List<movie_reserve_pojo> reservedMovies = new ArrayList<>();
}