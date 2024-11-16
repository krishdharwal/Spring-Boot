package user.pojo;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
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
  List<movie_pojo> myMovies = new ArrayList<>();
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
}
