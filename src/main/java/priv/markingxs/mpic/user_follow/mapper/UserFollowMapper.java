package priv.markingxs.mpic.user_follow.mapper;

import org.apache.ibatis.annotations.Mapper;
import priv.markingxs.mpic.user_follow.entity.UserFollow;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 用户关注用户记录表 Mapper 接口
 * </p>
 *
 * @author MARKinGxS
 * @since 2020-01-29
 */

@Mapper
public interface UserFollowMapper extends BaseMapper<UserFollow> {

}
