package priv.markingxs.mpic.user_login.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import priv.markingxs.mpic.user_login.entity.UserLogin;
import priv.markingxs.mpic.user_login.service.IUserLoginService;

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
    @Qualifier("iUserLoginService")
    private IUserLoginService iUserLoginService;

    @RequestMapping(value = "/getLoginInfo",method = RequestMethod.GET)
    public List<UserLogin> getLoginInfo(){
        return iUserLoginService.getLoginInfo();
    }

}

