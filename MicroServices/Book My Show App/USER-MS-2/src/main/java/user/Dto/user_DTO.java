package user.Dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import user.Enum.Roles_enum;
import user.pojo.movie_reserve_pojo;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class user_DTO {
    @Id
    ObjectId id;
    String name;
    String password;
    String mail;
    String place;
    Roles_enum Role;
    List<movie_reserve_pojo> reservedMovies = new ArrayList<>();
}
