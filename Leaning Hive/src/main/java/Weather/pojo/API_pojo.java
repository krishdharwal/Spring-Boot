package Weather.pojo;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "WeatherAPI")
@Data
public class API_pojo {

     public String key;
    public String value;

}
