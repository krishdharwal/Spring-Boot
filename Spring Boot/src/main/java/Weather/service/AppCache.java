package Weather.service;

import Weather.pojo.API_pojo;
import Weather.repo.repoWeather;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// app-cache is used for add some data , line , link
// and can be continusoly changing it
// and by not changing it in code it can be directly change in database

// In this class i have put my weatherStack link in Atlus
// and fetch it from db and named as app-cache
// and replaced with password which is in my properties
// by <> angular brackets

@Component
public class AppCache {
    public Map<String, String> appCache;

    @Autowired
    private repoWeather repo;

    @PostConstruct
    public void getApi() {
        appCache = new HashMap<>();
        List<API_pojo> configs = repo.findAll();
        for (API_pojo data : configs) {
            appCache.put(data.getKey(), data.getValue());
        }
    }
}
