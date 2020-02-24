package priv.markingxs.mpic.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import priv.markingxs.mpic.token.entity.TokenInfo;
import priv.markingxs.mpic.user_login.service.TokenToRedis;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

@Component
@Slf4j
public class PassportInterceptor implements HandlerInterceptor {

    @Autowired
    private StringRedisTemplate redisTemplate;

    //返回code类型：30000未传入身份信息 30001token校验不正确 30002校验token出错
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        /*TokenInfo tokenInfo = this.getUserToken(request);
        if (StringUtils.isBlank(tokenInfo.getUserEmail()) && StringUtils.isBlank(tokenInfo.getToken())) {
            // 返回登录
            this.resultReturn(response,30000);
            System.out.println("没有传入对应的身份信息，返回登录");
            return false;
        }
        try{
            System.out.println(tokenInfo.getToken());
            System.out.println(tokenInfo.getUserEmail());

            String token = redisTemplate.opsForValue().get(tokenInfo.getUserEmail());
            System.out.println(token);
            // String token = tokenToRedis.getValueFromRedis(tokenInfo.getUserEmail());
            if(token !=null && token.equals(tokenInfo.getToken())){
                log.info("token check is correct");
                return true;
            }else{
                this.resultReturn(response,30001);
                System.out.println("校验失败，返回登录");
                return false;
            }
        }catch(Exception e){
            this.resultReturn(response,30002);
            log.error("token check has a error");
            e.printStackTrace();
            return false;
        }*/
        return true;
    }

    private TokenInfo getUserToken(HttpServletRequest request){
        TokenInfo tokenInfo = new TokenInfo();
        String userEmail = request.getHeader("useremail");
        String userToken = request.getHeader("token");
        if(StringUtils.isNotBlank(userEmail) && StringUtils.isNotBlank(userToken)){
            tokenInfo.setUserEmail(userEmail);
            tokenInfo.setToken(userToken);
        }
        return tokenInfo;
    }

    private void resultReturn(HttpServletResponse response,Integer code){
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        PrintWriter out = null;
        try{
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("code",code);
            out = response.getWriter();
            out.append(jsonObject.toString());
        }catch (Exception e){
            e.printStackTrace();
            log.error("JSON put action has a error");
        }
    }


}
