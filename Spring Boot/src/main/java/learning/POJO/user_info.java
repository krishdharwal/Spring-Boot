package learning.POJO;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "User_info")
@Data
public class user_info {
    @Id
    private ObjectId id;
    private String info;
}
