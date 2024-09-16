package Weather.controller;

import Weather.pojo.pojo_weather;
import Weather.service.weather_services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/weather")
public class weather_controller {
    @Autowired
    private weather_services service;

    @GetMapping("{city}")
    public ResponseEntity<?> CurrentWeather(@PathVariable String city){
        pojo_weather body =  service.getWeather(city);
        String output = "";
        if (body != null){
            output ="|            " + city + "           |\n" +
                    "| Temprature  ->  " + body.getCurrent().temperature +"°\n"+
                    "| Humidity    ->  " + body.getCurrent().humidity + "%\n"+
                    "| Is Day      ->  " + body.getCurrent().isDay + "\n"+
                    "| Weather Description : " + body.getCurrent().weatherDescriptions ;
        }
        return new ResponseEntity<>(output, HttpStatus.ACCEPTED);
    }

    @GetMapping
    public String health(){
        return "--ready--";
    }

}
