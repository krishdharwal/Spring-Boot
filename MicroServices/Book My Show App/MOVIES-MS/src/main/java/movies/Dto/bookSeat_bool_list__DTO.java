package movies.Dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class bookSeat_bool_list__DTO {
    List<Boolean> seats = new ArrayList<>();
}
