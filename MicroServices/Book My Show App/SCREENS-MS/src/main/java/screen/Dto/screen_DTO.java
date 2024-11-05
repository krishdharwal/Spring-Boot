package screen.Dto;

import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

public class screen_DTO {
    ObjectId id;
    int screenNumber;
    int totalSeats;
    int seatsLeft;
    List<Boolean> bookedSeats = new ArrayList<>();
}
