package Weather.service;

import Weather.pojo.pojo_weather;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.PropertyEditorRegistrar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class Redis_service {

    @Autowired
    private RedisTemplate redisTemplate;

    public <T> T get(String key,Class<T> classType){
        try {
            // getting the value of the key
            Object body = redisTemplate.opsForValue().get(key);
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(body.toString(), classType);
        }catch (Exception e){
            log.error("---error in redis fetching process---" + e);
            return null;
        }
    }

    public void save(String key,Object body,Long time){
        try {
                ObjectMapper objectMapper = new ObjectMapper();
                String jsonValue = objectMapper.writeValueAsString(body);
                redisTemplate.opsForValue().set(key, jsonValue, time, TimeUnit.SECONDS);

        } catch (Exception e){
            log.error("error in redis service save" + e);
        }

    }

//    public static void main(String[] args) {
//        Redis_service redis = new Redis_service();
//       Object ans = redis.redisTemplate.opsForValue().get("weathermandi");
//        System.out.println(ans);
//        }

}
