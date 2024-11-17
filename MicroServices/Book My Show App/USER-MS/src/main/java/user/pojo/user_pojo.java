package user.pojo;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import movies.pojo.Current_Movies_pojo;
import movies.pojo.movie_pojo;
import org.bson.LazyBSONList;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import user.Enum.Roles_enum;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class user_pojo {

    @Id
    ObjectId id;
    @NonNull
    String name;
    @NonNull
    String password;
    String mail;
    String place;
    Roles_enum Role;
    List<movie_reserve_pojo> reservedMovies = new ArrayList<>();
    List<Current_Movies_pojo> MyMovies = new ArrayList<>();

}
