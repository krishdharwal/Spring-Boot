package org.CompanyMicroService.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.CompanyMicroService.pojo.companyMS_pojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class Redis_service_company {

    @Autowired
    private RedisTemplate redisTemplate;

    public void set(String key,Object company) throws JsonProcessingException {
        try {
            assert company != null;
            ObjectMapper mapper = new ObjectMapper();
            // Convert the company to String using object mapper and save it to redis
            redisTemplate.opsForValue().set(key, mapper.writeValueAsString(company));
        }catch (Exception e){
            log.error("error in set in Redis_service_company");
        }
    }


    public <T> T get(String key,Class<T> type) throws JsonProcessingException {
        try {
            // find the value
            Object body = redisTemplate.opsForValue().get(key);
            assert body != null;
            ObjectMapper mapper = new ObjectMapper();
            // read the value using object mapper and return it with company type
            return mapper.readValue(body.toString(), type);
        }catch (Exception e){
            log.error("error in get in Redis_service_company");
            return null;
        }

    }
}
