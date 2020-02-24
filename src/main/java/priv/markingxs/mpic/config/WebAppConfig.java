package priv.markingxs.mpic.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class WebAppConfig implements WebMvcConfigurer {



    @Bean
    public PassportInterceptor getPassportInterceptor(){
        return new PassportInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(getPassportInterceptor()).addPathPatterns("/**").excludePathPatterns("/user_login/user-login/**");
    }


}
