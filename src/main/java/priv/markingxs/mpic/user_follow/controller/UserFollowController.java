package priv.markingxs.mpic.user_follow.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import priv.markingxs.mpic.base.responseBase.entity.ResponseBase;
import priv.markingxs.mpic.base.responseBase.service.BaseResponseApiService;
import priv.markingxs.mpic.user_follow.service.IUserFollowService;
import priv.markingxs.mpic.user_login.entity.UserLogin;
import priv.markingxs.mpic.user_login.service.IUserLoginService;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 用户关注用户记录表 前端控制器
 * </p>
 *
 * @author MARKinGxS
 * @since 2020-01-29
 */
@RestController
@RequestMapping("/user_follow/user-follow")
public class UserFollowController {

    @Autowired
    @Qualifier("userLoginservice")
    private IUserLoginService iUserLoginService;
    @Autowired
    @Qualifier("userFollowService")
    private IUserFollowService iUserFollowService;

    //添加关注
    // 80001关注录入失败
    @RequestMapping(value = "/addFollow",method = RequestMethod.GET)
    public ResponseBase addFollow(@RequestParam("pageUserEmail") String pageUserEmail, HttpServletRequest request){
        String userEmail = request.getHeader("useremail");
        QueryWrapper<UserLogin> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_email", userEmail);
        String userId = iUserLoginService.getOne(queryWrapper).getUserId();

        QueryWrapper<UserLogin> queryWrapper2 = new QueryWrapper<>();
        queryWrapper2.eq("user_email",pageUserEmail);
        String pageUserId = iUserLoginService.getOne(queryWrapper2).getUserId();

        if(iUserFollowService.addFollow(pageUserId,userId)){
            return BaseResponseApiService.customResult(20000,"添加关注成功",null);
        }else{
            return BaseResponseApiService.customResult(80001,"添加关注失败",null);
        }
    }

    //取消关注
    // 80002取消关注记录失败
    @RequestMapping(value = "/removeFollow",method = RequestMethod.GET)
    public ResponseBase removeFollow(@RequestParam("pageUserEmail") String pageUserEmail, HttpServletRequest request){
        String userEmail = request.getHeader("useremail");
        QueryWrapper<UserLogin> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_email", userEmail);
        String userId = iUserLoginService.getOne(queryWrapper).getUserId();

        QueryWrapper<UserLogin> queryWrapper2 = new QueryWrapper<>();
        queryWrapper2.eq("user_email",pageUserEmail);
        String pageUserId = iUserLoginService.getOne(queryWrapper2).getUserId();

        if(iUserFollowService.removeFollow(pageUserId,userId)){
            return BaseResponseApiService.customResult(20000,"取消关注成功",null);
        }else{
            return BaseResponseApiService.customResult(80001,"取消关注失败",null);
        }
    }

    //是否关注
    @RequestMapping(value = "/ifFollow",method = RequestMethod.GET)
    public ResponseBase ifFollow(@RequestParam("pageUserEmail") String pageUserEmail, HttpServletRequest request){
        String userEmail = request.getHeader("useremail");
        QueryWrapper<UserLogin> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_email", userEmail);
        String userId = iUserLoginService.getOne(queryWrapper).getUserId();

        QueryWrapper<UserLogin> queryWrapper2 = new QueryWrapper<>();
        queryWrapper2.eq("user_email",pageUserEmail);
        String pageUserId = iUserLoginService.getOne(queryWrapper2).getUserId();

        Integer code = iUserFollowService.ifFollow(pageUserId,userId);
        if(code == 0){
            return BaseResponseApiService.customResult(0,"未关注",null);
        }else{
            return BaseResponseApiService.customResult(1,"已关注",null);
        }
    }

}

