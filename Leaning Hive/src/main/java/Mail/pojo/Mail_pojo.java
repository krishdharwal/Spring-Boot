package Mail.pojo;

import lombok.Data;
import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "mail_users")
@Data
public class Mail_pojo {
    @Id
    ObjectId id;

    @NonNull
    public String username;
    @NonNull
    public String password;
    public String mailAdress;
    public Sentiments sentiments;
}
