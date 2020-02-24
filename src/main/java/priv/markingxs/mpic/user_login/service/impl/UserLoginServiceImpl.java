package priv.markingxs.mpic.user_login.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import priv.markingxs.mpic.user.entity.User;
import priv.markingxs.mpic.user.service.IUserService;
import priv.markingxs.mpic.user_login.entity.UserLogin;
import priv.markingxs.mpic.user_login.entity.UserLoginPage;
import priv.markingxs.mpic.user_login.mapper.UserLoginMapper;
import priv.markingxs.mpic.user_login.service.IUserLoginService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import priv.markingxs.mpic.user_login.service.TokenToRedis;
import priv.markingxs.mpic.utils.IDGenerator;
import priv.markingxs.mpic.utils.Sha;
import priv.markingxs.mpic.utils.TokenGenerator;


import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户登录信息表 服务实现类
 * </p>
 *
 * @author MARKinGxS
 * @since 2020-01-29
 */
@Service("userLoginservice")
public class UserLoginServiceImpl extends ServiceImpl<UserLoginMapper, UserLogin> implements IUserLoginService {

    @Autowired
    private UserLoginMapper userLoginMapper;

    @Autowired
    private TokenToRedis tokenToRedis;

    @Autowired
    @Qualifier("userservice")
    private IUserService iUserService;

    @Override
    public List<UserLogin> getLoginInfo() {
        List<UserLogin> userLoginList = userLoginMapper.selectAllLoginInfo();
        for (UserLogin user : userLoginList) {
            System.out.println("user:"+user);
        }
        return userLoginMapper.selectAllLoginInfo();
    }

    @Override
    public UserLoginPage queryUserLoginInfoForPage(Integer pageIndex, Integer pageSize){
        IPage<UserLogin> page = new Page<UserLogin>(pageIndex,pageSize);
        page = userLoginMapper.selectPage(page,null);
        UserLoginPage userLoginPage = new UserLoginPage();
        userLoginPage.setCurrent(pageIndex);
        userLoginPage.setSize(pageSize);
        userLoginPage.setTotal(page.getTotal());
        userLoginPage.setUserLoginList(page.getRecords());
        return userLoginPage;
    }


    @Override
    public Map<String, Object> checkUserRegister(String userEmail, String userPassword) {
        Map<String,Object> resultMap = new LinkedHashMap<>();

        QueryWrapper<UserLogin> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_email",userEmail);
        UserLogin userLogin = this.getOne(queryWrapper);
        if(userLogin != null){
            resultMap.put("code",50001);
        }else{
            String userId = IDGenerator.userIdGenerate();


            UserLogin userLoginNewOne = new UserLogin();
            userLoginNewOne.setUserEmail(userEmail);
            userLoginNewOne.setUserPassword(Sha.SHA512(userPassword));
            userLoginNewOne.setUserId(userId);
            boolean result = this.save(userLoginNewOne);

            User user = new User();
            user.setUserId(userId);
            iUserService.save(user);

            if(result){
                resultMap.put("code",20000);
            }else{
                resultMap.put("code",50000);
            }
        }
        return resultMap;
    }

    @Override
    public Map<String,Object> checkUserLogin(String userEmail, String password) {

        String token = TokenGenerator.tokenGenerate();
        Map<String,Object> resultMap = new LinkedHashMap<>();

        QueryWrapper<UserLogin> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_email",userEmail);
        UserLogin userLogin = this.getOne(queryWrapper);
        if(userLogin == null){
            resultMap.put("code",50002);
        }else{
            if(userLogin.getUserPassword().equals(Sha.SHA512(password))){
                tokenToRedis.tokenToRedis(userEmail, token);
                resultMap.put("code",20000);
                resultMap.put("token",token);
            }else{
                resultMap.put("code",50003);
            }
        }

        return resultMap;
    }



}
