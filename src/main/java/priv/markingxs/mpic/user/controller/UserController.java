package priv.markingxs.mpic.user.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;
import priv.markingxs.mpic.user.entity.User;
import priv.markingxs.mpic.user.service.IUserService;


/**
 * <p>
 * 用户表，存储用户信息（除用户登录信息） 前端控制器
 * </p>
 *
 * @author MARKinGxS
 * @since 2020-02-03
 */
@RestController
@RequestMapping("/user/user")
public class UserController {

    @Autowired
    @Qualifier("userservice")
    private IUserService iUserService;

    @RequestMapping(value = "/getUserForUserID/{userid}",method = RequestMethod.GET)
    public User getUserForUserID(@PathVariable("userid") String userid){
        return iUserService.getUserForUserID(userid);
    }

}

