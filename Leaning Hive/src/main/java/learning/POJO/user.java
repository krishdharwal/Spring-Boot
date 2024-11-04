package learning.POJO;

import lombok.Data;
import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "User_Data")
@Data
public class user {
    @Id
    public ObjectId id;

    @Indexed(unique = true)
    @NonNull
    public String username;
    @NonNull
    public String password;

    public List<String> roles;

    @DBRef
    public List<user_info> info = new ArrayList<>();
}
