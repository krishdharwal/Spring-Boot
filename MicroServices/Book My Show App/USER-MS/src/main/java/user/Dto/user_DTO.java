package user.Dto;

import io.micrometer.common.lang.NonNullApi;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.bson.types.ObjectId;
import user.Enum.Roles_enum;
import user.pojo.movie_reserve_pojo;

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
