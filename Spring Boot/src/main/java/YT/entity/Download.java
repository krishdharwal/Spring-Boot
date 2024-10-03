package YT.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "downloads")
@Data
@NoArgsConstructor
public class Download {

    @Id
    private String id;
    private String videoUrl;
    private String downloadStatus;
    private String filePath;
    private Date requestTime;
    private Date completionTime;

}

