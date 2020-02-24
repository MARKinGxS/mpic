package priv.markingxs.mpic.user_login.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import priv.markingxs.mpic.base.responseBase.entity.ResponseBase;
import priv.markingxs.mpic.base.responseBase.service.BaseResponseApiService;
import priv.markingxs.mpic.user.service.IUserService;
import priv.markingxs.mpic.user_login.entity.UserLogin;
import priv.markingxs.mpic.user_login.entity.UserLoginPage;
import priv.markingxs.mpic.user_login.service.IUserLoginService;

import org.springframework.web.bind.annotation.*;
import priv.markingxs.mpic.user_login.service.MailService;
import priv.markingxs.mpic.user_login.service.TokenToRedis;
import priv.markingxs.mpic.user_login.service.VerifyCodeToRedis;
import priv.markingxs.mpic.utils.IDGenerator;
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
    @Autowired
    @Qualifier("mailservice")
    private MailService mailService;


    @Autowired
    private TokenToRedis tokenToRedis;
    @Autowired
    private VerifyCodeToRedis verifyCodeToRedis;


    //获取所有的用户登陆数据
    @RequestMapping(value = "/getLoginInfo",method = RequestMethod.GET)
    public List<UserLogin> getLoginInfo(){
        return iUserLoginService.getLoginInfo();
    }

    //分页查询 只是用来实现练习 系统可能不需要 类似的在作品查询会需要
    @RequestMapping(value = "/getLoginInfoForPage",method = RequestMethod.GET)
    public ResponseBase getLoginInfoForPage(@RequestParam("pageIndex") Integer pageIndex ){
        UserLoginPage userLoginPage = iUserLoginService.queryUserLoginInfoForPage(pageIndex,2);
        ResponseBase responseBase = null;
        if(userLoginPage!=null){
            responseBase = BaseResponseApiService.customResult(20000,"success",userLoginPage);
        }
        return responseBase;
    }

    //管理系统登录controller
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


    //注册验证码获取
    @RequestMapping(value = "/sendMail",method = RequestMethod.GET)
    public String sendMail(@RequestParam("sendid") String sendId){
        String randomNum = String.valueOf((int)((Math.random()*9+1)*100000));
        mailService.sendMail(sendId,"Mpic Verify Code",randomNum);
        verifyCodeToRedis.verifyCodeToRedis(sendId+"-code",randomNum);
        return "send mail success";
    }

    //客户系统注册
    // 20000成功code
    // 50000注册失败
    // 50001用户已存在
    // 40001验证码不存在需重新获取
    // 40002验证码为空
    // 40003验证码不正确
    @RequestMapping(value = "/client-Register",method = RequestMethod.POST)
    public ResponseBase registerUser(@RequestBody Map<String,Object> userMap){

        String userEmail = (String) userMap.get("useremail");
        ResponseBase responseBase = null;
        String correctCode = verifyCodeToRedis.getValueFromRedis(userEmail+"-code");
        if(correctCode==null){
            responseBase = BaseResponseApiService.customResult(40001,"验证码不存在，请重新获取",null);
        }else{
            String userInputVerifyCode = (String)userMap.get("verifycode");
            if(!userInputVerifyCode.isBlank()){
                if(userInputVerifyCode.equals(correctCode)){
                    //暂时未确定
                    String userPassword = (String) userMap.get("userpassword");

                    Map<String,Object> resultMap = iUserLoginService.checkUserRegister(userEmail,userPassword);
                    Integer code = (Integer) resultMap.get("code");

                    switch (code){
                        case 20000:
                            responseBase = BaseResponseApiService.successResultNoData();
                            break;
                        case 50000:
                            responseBase = BaseResponseApiService.customResult(50000,"register is failed",null);
                            break;
                        case 50001:
                            responseBase = BaseResponseApiService.customResult(50001,"user is already exist",null);
                            break;
                    }
                }else{
                    responseBase = BaseResponseApiService.customResult(40003,"验证码不正确",null);
                }
            }else{
                responseBase = BaseResponseApiService.customResult(40002,"验证码不能为空",null);
            }
        }
        return responseBase;

    }

    //客户系统登录 20000成功 50002用户不存在 50003用户密码错误
    @RequestMapping(value = "/client-Login",method = RequestMethod.POST)
    public ResponseBase loginUser(@RequestBody Map<String,Object> userMap){
        String userEmail = (String) userMap.get("useremail");
        String userPassword = (String) userMap.get("userpassword");
        ResponseBase responseBase = null;

        Map<String,Object> resultMap = iUserLoginService.checkUserLogin(userEmail,userPassword);
        Integer code = (Integer) resultMap.get("code");


        switch (code){
            case 20000:
                Map<String,String> tokenMap = new LinkedHashMap<>();
                Map<String,String> infoMap = tokenToRedis.tokenToRedis(userEmail,(String)resultMap.get("token"));
                tokenMap.put("token",infoMap.get(userEmail));
                responseBase = BaseResponseApiService.customResult(code,"login success",tokenMap);
                break;
            case 50002:
                responseBase = BaseResponseApiService.customResult(code,"Id is not exist",null);
                break;
            case 50003:
                responseBase = BaseResponseApiService.customResult(code,"Password is not correct",null);
                break;
        }
        return responseBase;
    }

    //客户系统忘记密码
    @RequestMapping(value = "/userForgetPass",method = RequestMethod.POST)
    public ResponseBase userForgetPass(@RequestBody Map<String,Object> userMap){
        String userEmail = (String) userMap.get("useremail");
        ResponseBase responseBase = null;
        String correctCode = verifyCodeToRedis.getValueFromRedis(userEmail+"-code");
        if(correctCode==null){
            responseBase = BaseResponseApiService.customResult(40001,"验证码不存在，请重新获取",null);
        }else{
            String userInputVerifyCode = (String)userMap.get("verifycode");
            if(!userInputVerifyCode.isBlank()){
                if(userInputVerifyCode.equals(correctCode)){
                    QueryWrapper<UserLogin> queryWrapper = new QueryWrapper<>();
                    queryWrapper.eq("user_email",userEmail);
                    UserLogin userLogin = iUserLoginService.getOne(queryWrapper);
                    if(userLogin != null){
                        userLogin.setUserPassword(Sha.SHA512((String) userMap.get("userpassword")));
                        iUserLoginService.saveOrUpdate(userLogin);
                        responseBase = BaseResponseApiService.successResultNoData();
                    }else{
                        responseBase = BaseResponseApiService.customResult(50002,"Id is not exist",null);
                    }
                }else{
                    responseBase = BaseResponseApiService.customResult(40003,"验证码不正确",null);
                }
            }else{
                responseBase = BaseResponseApiService.customResult(40002,"验证码不能为空",null);
            }
        }

        return responseBase;
    }






    //redis测试
    @RequestMapping(value = "/test",method = RequestMethod.GET)
    public String test(){
        tokenToRedis.tokenToRedis("test","testResult");
        System.out.println(tokenToRedis.getValueFromRedis("test"));
        return tokenToRedis.getValueFromRedis("test");
    }


}

