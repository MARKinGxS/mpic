package priv.markingxs.mpic.user.controller;


import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import priv.markingxs.mpic.base.responseBase.entity.ResponseBase;
import priv.markingxs.mpic.base.responseBase.service.BaseResponseApiService;
import priv.markingxs.mpic.user.entity.User;
import priv.markingxs.mpic.user.service.IUserService;

import javax.servlet.http.HttpServletRequest;


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

    //获取user个人信息对象
    @RequestMapping(value = "/getUserForUserID/{userid}",method = RequestMethod.GET)
    public User getUserForUserID(@PathVariable("userid") String userid){
        return iUserService.getUserForUserID(userid);
    }

    //保存用户个人信息资料
    // 5001头像格式不正确
    // 5002用户昵称数据有误
    // 5003用户信息保存失败
    @RequestMapping(value = "/saveUserInfo", method = RequestMethod.POST)
    public ResponseBase saveUserInfo(@RequestParam("fileName") MultipartFile file, HttpServletRequest request){

        String fileType = file.getContentType();
        ResponseBase responseBase = null;

        if(fileType.equals("image/jpg") || fileType.equals("image/png") || fileType.equals("image/jpeg")){
            String userName = request.getParameter("userNickname");
            if(StringUtils.isBlank(userName)){
                responseBase = BaseResponseApiService.customResult(5002, "User's name is wrong data", null);
            }else{
                String userEmail = request.getHeader("useremail");
                if(iUserService.saveUserInfo(userEmail,userName,file)){
                 responseBase = BaseResponseApiService.customResult(20000,"保存资料成功",null);
                }else{
                    responseBase = BaseResponseApiService.customResult(5003,"保存资料失败",null);
                }
            }

        }else{
            responseBase = BaseResponseApiService.customResult(5001,"头像格式不正确",null);
        }

        return responseBase;
    }

}
