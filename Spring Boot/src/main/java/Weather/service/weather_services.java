package Weather.service;

import Weather.pojo.pojo_weather;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class weather_services {
    @Autowired
    private Redis_service redisService;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private AppCache API;

    // that's how to fetch data from properties file
    @Value("${weather_API}")
    private String apiKey;

    public pojo_weather getWeather(String city){
        try {
            // now this is giving me the value of saved weather of city of pojo_weather type
            pojo_weather data = redisService.get("weather_of_"+ city, pojo_weather.class);
            if (data != null) {
                return data;
            } else {
                String apiFromCache = API.appCache.get("weatherApi").replace("<ApiKey>", apiKey).replace("<City>", city);
                ResponseEntity<pojo_weather> response = restTemplate.exchange(apiFromCache, HttpMethod.GET, null, pojo_weather.class);
                pojo_weather body = response.getBody();
                if (body != null) {
                    redisService.save("weather_of_"+ city, body, 300L);
                    return body;
                }
            }
        } catch (Exception e){
            log.error("problem in get weather -----------" + e);
        }
        return null;
    }
}




// stored in db
//    private static final String API = "https://api.weatherstack.com/current?access_key=API_KEY&query=CITY";

