package priv.markingxs.mpic.user_login.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import priv.markingxs.mpic.user.service.IUserService;
import priv.markingxs.mpic.user_login.entity.UserLogin;
import priv.markingxs.mpic.user_login.service.IUserLoginService;

import org.springframework.web.bind.annotation.*;
import priv.markingxs.mpic.utils.Sha;

import java.util.LinkedHashMap;
import java.util.Map;

import java.util.List;

/**
 * <p>
 * 用户登录信息表 前端控制器
 * </p>
 *
 * @author MARKinGxS
 * @since 2020-01-29
 */
@RestController
@RequestMapping("/user_login/user-login")
public class UserLoginController {

    @Autowired
    @Qualifier("userLoginservice")
    private IUserLoginService iUserLoginService;
    @Autowired
    @Qualifier("userservice")
    private IUserService iUserService;

    @RequestMapping(value = "/getLoginInfo",method = RequestMethod.GET)
    public List<UserLogin> getLoginInfo(){
        return iUserLoginService.getLoginInfo();
    }

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public Map<String,Object> login(@RequestBody Map userMap){
        System.out.println("username:"+userMap.get("username"));
        //用户登陆输入的登录名
        String username = (String) userMap.get("username");
        //用户登录输入的密码
        String password = (String) userMap.get("password");
        List<UserLogin> userLog = iUserLoginService.getLoginInfo();
        Map<String,Object> map = new LinkedHashMap<>();

        for(UserLogin userLogin:userLog){
            if(userLogin.getUserEmail().equals(username)){
                if((iUserService.getUserForUserID(userLogin.getUserId()).getUserCharacter()-1)==0){
                    Map<String,String> littleMap = new LinkedHashMap<>();
                    if(Sha.SHA512(password).equals(userLogin.getUserPassword())){
                        littleMap.put("token","admin-token");
                        map.put("code",20000);
                        map.put("data",littleMap);
                    }else{
                        //50000状态码为用户密码错误
                        map.put("code",50000);
                    }
                }else{
                    //50001状态码为存在此用户名但不是管理员
                    map.put("code",50001);
                }
            }else{
                //50002状态码为不存在此用户名
                map.put("code",50002);
            }
        }
        return map;
    }

    @RequestMapping(value = "/login2",method = RequestMethod.POST)
    public Map<String,Object> login2(@RequestBody Map userMap){
        Map<String,Object> map = new LinkedHashMap<>();
        Map<String,String> littleMap = new LinkedHashMap<>();
        littleMap.put("token","admin-token");
        map.put("code",20000);
        map.put("data",littleMap);
        return map;
    }
}

