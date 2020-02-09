package priv.markingxs.mpic.user.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import priv.markingxs.mpic.user.entity.User;
import priv.markingxs.mpic.user.mapper.UserMapper;
import priv.markingxs.mpic.user.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户表，存储用户信息（除用户登录信息） 服务实现类
 * </p>
 *
 * @author MARKinGxS
 * @since 2020-02-07
 */
@Service("userservice")
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Autowired
    private UserMapper userMapper;

    public User getUserForUserID(String userid){
        return userMapper.getUserForUserID(userid);
    }

}
