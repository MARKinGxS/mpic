package priv.markingxs.mpic.user_login.service;

import priv.markingxs.mpic.user_login.entity.UserLogin;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

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
}
