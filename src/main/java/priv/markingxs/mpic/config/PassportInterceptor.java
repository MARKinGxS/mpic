package priv.markingxs.mpic.config;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class PassportInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("拦截器preHandle method");
        String token = null;
        System.out.println("request.getCookies():"+request.getCookies());
        if(request.getCookies() !=null){
            for(Cookie cookie:request.getCookies()){
                if(cookie.getName().equals("token")){
                    token = cookie.getValue();
                    System.out.println("拦截器 -截取- token: "+token);
                    break;
                }
            }
        }
        return true;
    }
}
