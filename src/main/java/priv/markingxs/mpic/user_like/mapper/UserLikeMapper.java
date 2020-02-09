package priv.markingxs.mpic.user_like.mapper;

import org.apache.ibatis.annotations.Mapper;
import priv.markingxs.mpic.user_like.entity.UserLike;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 用户点赞作品记录表 Mapper 接口
 * </p>
 *
 * @author MARKinGxS
 * @since 2020-01-29
 */
@Mapper
public interface UserLikeMapper extends BaseMapper<UserLike> {

}
