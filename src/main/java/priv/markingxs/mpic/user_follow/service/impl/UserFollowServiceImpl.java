package priv.markingxs.mpic.user_follow.service.impl;

import priv.markingxs.mpic.user_follow.entity.UserFollow;
import priv.markingxs.mpic.user_follow.mapper.UserFollowMapper;
import priv.markingxs.mpic.user_follow.service.IUserFollowService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户关注用户记录表 服务实现类
 * </p>
 *
 * @author MARKinGxS
 * @since 2020-01-29
 */
@Service
public class UserFollowServiceImpl extends ServiceImpl<UserFollowMapper, UserFollow> implements IUserFollowService {

}
