package cinema.pojo;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

@Data
@NoArgsConstructor
public class cinema_pojo {
    @Id
    ObjectId id;
    @NonNull
    String name;
    @NonNull
    String address;
    int totalScreens;
}
