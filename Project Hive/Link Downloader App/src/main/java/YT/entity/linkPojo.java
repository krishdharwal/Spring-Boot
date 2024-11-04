package YT.entity;


import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;


@Document(collection = "yt history")
@Data
@EntityScan
public class linkPojo {
    @Id
    public ObjectId id;
    public LocalDateTime dateTime;
    public String link;
}
