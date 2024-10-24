package ReviewsMS.DTOs;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;

@Data
@NoArgsConstructor
public class review_DTo {
    ObjectId id;
    String review;
    int rating;
    String companyName;
}
