package priv.markingxs.mpic.user_login.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import priv.markingxs.mpic.user_login.entity.UserLogin;
import priv.markingxs.mpic.user_login.mapper.UserLoginMapper;
import priv.markingxs.mpic.user_login.service.IUserLoginService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 用户登录信息表 服务实现类
 * </p>
 *
 * @author MARKinGxS
 * @since 2020-01-29
 */
@Service
@Component("iUserLoginService")
public class UserLoginServiceImpl extends ServiceImpl<UserLoginMapper, UserLogin> implements IUserLoginService {


    private UserLoginMapper userLoginMapper = super.getBaseMapper();

    @Override
    public List<UserLogin> getLoginInfo() {
        return userLoginMapper.selectAllLoginInfo();
    }
}
