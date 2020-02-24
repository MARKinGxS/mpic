package priv.markingxs.mpic.user_login.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * 验证码存入redis，key统一用(userid+"-code")组成
 */

@Service
public class VerifyCodeToRedis {

    @Autowired
    private StringRedisTemplate redisTemplate;

    public void verifyCodeToRedis(String key,String value){
        redisTemplate.opsForValue().set(key, value);
        redisTemplate.expire(key, 300, TimeUnit.SECONDS);
    }

    public String getValueFromRedis(String key){
        return redisTemplate.opsForValue().get(key);
    }

}
