package ReviewsMS.pojo;

import com.mongodb.lang.NonNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document (collection = "ReviewMS Data")
@Data
@NoArgsConstructor
public class reviews_pojo {
    @Id
    private ObjectId id;
    private String review;
    @NonNull
    private int rating;
    }
