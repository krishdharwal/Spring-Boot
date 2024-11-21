package movies.pojo;


import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import movies.Enum.Roles_enum;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Document("Users Data")
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

}

