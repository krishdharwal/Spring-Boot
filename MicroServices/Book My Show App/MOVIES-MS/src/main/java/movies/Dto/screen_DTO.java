package movies.Dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;


@Data
@NoArgsConstructor
public class screen_DTO {
    public ObjectId id;
    public String name;
    public int TotalSeats;
    public List<Boolean> seatsList = new ArrayList<>(Collections.nCopies(TotalSeats,false));
}
