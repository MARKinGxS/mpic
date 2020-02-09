package priv.markingxs.mpic.user.service;

import priv.markingxs.mpic.user.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 用户表，存储用户信息（除用户登录信息） 服务类
 * </p>
 *
 * @author MARKinGxS
 * @since 2020-02-07
 */
public interface IUserService extends IService<User> {

    User getUserForUserID(String userid);
}
