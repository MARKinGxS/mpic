package priv.markingxs.mpic.user_login.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * token存入redis,如果同一用户重复登录，token的时效重置
 */

@Service
public class TokenToRedis {

    @Autowired
    private StringRedisTemplate redisTemplate;

    public Map<String,String> tokenToRedis(String key,String value){
        Map<String,String> infoMap = new HashMap<>();
        if(redisTemplate.opsForValue().get(key)==null){
            redisTemplate.opsForValue().set(key, value);
            infoMap.put(key, value);
            redisTemplate.expire(key, 3000, TimeUnit.SECONDS);
        }else{
            redisTemplate.expire(key, 3000, TimeUnit.SECONDS);
            infoMap.put(key,redisTemplate.opsForValue().get(key));
        }
        return infoMap;
    }



    public String getValueFromRedis(String key){
        return redisTemplate.opsForValue().get(key);
    }

}
