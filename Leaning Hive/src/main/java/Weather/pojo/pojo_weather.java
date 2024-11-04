package Weather.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class pojo_weather {
    public Current current;

    public class Current{
        public int temperature;

        @JsonProperty("weather_descriptions")
        public List<String> weatherDescriptions;
        public int pressure;
        public int humidity;
        public int cloudcover;
        public int feelslike;
        public int uv_index;
        public int visibility;

        @JsonProperty("is_day")
        public String isDay;
    }

}
