package priv.markingxs.mpic.works.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import priv.markingxs.mpic.utils.SerializeUtil;
import priv.markingxs.mpic.works.entity.Works;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class WorksRankingToRedis {

    @Autowired
    private StringRedisTemplate redisTemplate;

    public void worksRankingToRedis(String key,List<Works> worksList){
        Map<String, List<Works>> workMap = new HashMap<>();
        String strObj = new String(SerializeUtil.serialize(worksList));
        redisTemplate.opsForValue().set(key,strObj);
        redisTemplate.expire(key,5, TimeUnit.HOURS);
    }

    public List<Works> getRankingListFromRedis(String key){
        String strObj = redisTemplate.opsForValue().get(key);
        if(strObj == null){
            return null;
        }
        byte[] bytes = strObj.getBytes();
        List<Works> worksList = (List<Works>) SerializeUtil.deserialize(bytes);
        return worksList;
    }
}
