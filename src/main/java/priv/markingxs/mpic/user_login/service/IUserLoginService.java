package priv.markingxs.mpic.user_login.service;

import priv.markingxs.mpic.user_login.entity.UserLogin;
import com.baomidou.mybatisplus.extension.service.IService;
import priv.markingxs.mpic.user_login.entity.UserLoginPage;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户登录信息表 服务类
 * </p>
 *
 * @author MARKinGxS
 * @since 2020-01-29
 */
public interface IUserLoginService extends IService<UserLogin> {

    List<UserLogin> getLoginInfo();

    Map<String,Object> checkUserLogin(String userEmail, String password);

    Map<String,Object> checkUserRegister(String userEmail, String userPassword);

    UserLoginPage queryUserLoginInfoForPage(Integer pageIndex, Integer pageSize);
}
