package priv.markingxs.mpic.user_like.service.impl;

import priv.markingxs.mpic.user_like.entity.UserLike;
import priv.markingxs.mpic.user_like.mapper.UserLikeMapper;
import priv.markingxs.mpic.user_like.service.IUserLikeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户点赞作品记录表 服务实现类
 * </p>
 *
 * @author MARKinGxS
 * @since 2020-01-29
 */
@Service
public class UserLikeServiceImpl extends ServiceImpl<UserLikeMapper, UserLike> implements IUserLikeService {

}
