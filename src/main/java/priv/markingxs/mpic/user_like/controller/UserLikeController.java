package priv.markingxs.mpic.user_like.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import priv.markingxs.mpic.base.responseBase.entity.ResponseBase;
import priv.markingxs.mpic.base.responseBase.service.BaseResponseApiService;
import priv.markingxs.mpic.user_like.service.IUserLikeService;
import priv.markingxs.mpic.user_login.entity.UserLogin;
import priv.markingxs.mpic.user_login.service.IUserLoginService;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 用户点赞作品记录表 前端控制器
 * </p>
 *
 * @author MARKinGxS
 * @since 2020-01-29
 */
@RestController
@RequestMapping("/user_like/user-like")
public class UserLikeController {

    @Autowired
    @Qualifier("userLikeService")
    private IUserLikeService iUserLikeService;
    @Autowired
    @Qualifier("userLoginservice")
    private IUserLoginService iUserLoginService;

    //点赞作品
    @RequestMapping(value = "/addWorkLike", method = RequestMethod.GET)
    public ResponseBase addWorkLike(@RequestParam("workId") String workId, HttpServletRequest request){
        String userEmail = request.getHeader("useremail");
        QueryWrapper<UserLogin> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_email", userEmail);
        String userId = iUserLoginService.getOne(queryWrapper).getUserId();

        ResponseBase responseBase = null;

        QueryWrapper queryWrapper1 = new QueryWrapper();
        queryWrapper1.eq("user_id",userId);
        queryWrapper1.eq("work_id",workId);
        if(iUserLikeService.getOne(queryWrapper1)!=null){
            responseBase = BaseResponseApiService.customResult(70003,"已点赞，添加失败",null);
        }else{
            try{
                iUserLikeService.addLike(workId,userId);
                responseBase = BaseResponseApiService.successResultNoData();
            }catch (IllegalArgumentException e){
                responseBase = BaseResponseApiService.customResult(70001,"点赞录入失败",null);
            }
        }
        return responseBase;
    }

    //取消点赞作品
    @RequestMapping(value = "/removeWorkLike", method = RequestMethod.GET)
    public ResponseBase removeWorkLike(@RequestParam("workId") String workId, HttpServletRequest request){
        String userEmail = request.getHeader("useremail");
        QueryWrapper<UserLogin> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_email", userEmail);
        String userId = iUserLoginService.getOne(queryWrapper).getUserId();
        ResponseBase responseBase = null;

        QueryWrapper queryWrapper1 = new QueryWrapper();
        queryWrapper1.eq("user_id",userId);
        queryWrapper1.eq("work_id",workId);
        if(iUserLikeService.getOne(queryWrapper1)==null){
            responseBase = BaseResponseApiService.customResult(70002,"未点赞，取消失败",null);
        }else{
            try{
                iUserLikeService.removeLike(workId,userId);
                responseBase = BaseResponseApiService.successResultNoData();
            }catch(IllegalArgumentException e){
                responseBase = BaseResponseApiService.customResult(70002,"取消点赞失败",null);
            }
        }

        return responseBase;
    }

    //获取是否点赞
    @RequestMapping(value = "/getWorkLike", method = RequestMethod.GET)
    public ResponseBase getWorkLike(@RequestParam("workId") String workId, HttpServletRequest request){
        String userEmail = request.getHeader("useremail");
        QueryWrapper<UserLogin> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_email", userEmail);
        String userId = iUserLoginService.getOne(queryWrapper).getUserId();

        Integer code = iUserLikeService.ifWorkLike(workId,userId);
        ResponseBase responseBase = null;
        if(code == 0){
            responseBase = BaseResponseApiService.customResult(0,"未点赞",null);
        }else if(code == 1){
            responseBase = BaseResponseApiService.customResult(1,"已点赞",null);
        }
        return responseBase;
    }


}

