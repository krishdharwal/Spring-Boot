package cinema.pojo;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

import java.util.List;

@Data
@NoArgsConstructor
public class cinema_pojo {
    @Id
    ObjectId id;
    String name;
    int screens;
//    List
}
