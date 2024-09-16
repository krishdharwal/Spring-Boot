package learning;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootTest
public class Redis {

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    void setSomething(){
        redisTemplate.opsForValue().set("email","experiment47171@gmail.com");

//       Object email =  redisTemplate.opsForValue().get("email");
//        System.out.println(email);
    }

}
