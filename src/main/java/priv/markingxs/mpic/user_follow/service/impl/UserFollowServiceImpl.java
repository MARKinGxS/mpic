package priv.markingxs.mpic.user_follow.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
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
@Service("userFollowService")
public class UserFollowServiceImpl extends ServiceImpl<UserFollowMapper, UserFollow> implements IUserFollowService {


    @Override
    public Boolean addFollow(String pageUserId, String userId) {
        UserFollow userFollow = new UserFollow();
        userFollow.setUserFollowId(pageUserId);
        userFollow.setUserId(userId);
        return this.save(userFollow);
    }

    @Override
    public Boolean removeFollow(String pageUserId, String userId) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("user_id",userId);
        queryWrapper.eq("user_follow_id",pageUserId);
        return this.remove(queryWrapper);
    }

    @Override
    public Integer ifFollow(String pageUserId, String userId) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("user_id",userId);
        queryWrapper.eq("user_follow_id",pageUserId);
        UserFollow userFollow = this.getOne(queryWrapper);
        if(userFollow == null){
            return 0;
        }else{
            return 1;
        }
    }


}
