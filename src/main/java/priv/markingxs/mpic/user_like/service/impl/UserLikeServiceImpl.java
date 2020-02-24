package priv.markingxs.mpic.user_like.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import priv.markingxs.mpic.user_like.entity.UserLike;
import priv.markingxs.mpic.user_like.mapper.UserLikeMapper;
import priv.markingxs.mpic.user_like.service.IUserLikeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import priv.markingxs.mpic.works.entity.Works;
import priv.markingxs.mpic.works.mapper.WorksMapper;

/**
 * <p>
 * 用户点赞作品记录表 服务实现类
 * </p>
 *
 * @author MARKinGxS
 * @since 2020-01-29
 */
@Service("userLikeService")
public class UserLikeServiceImpl extends ServiceImpl<UserLikeMapper, UserLike> implements IUserLikeService {

    @Autowired
    private WorksMapper worksMapper;

    @Override
    @Transactional(rollbackFor = IllegalArgumentException.class)
    public void addLike(String workId, String userId) {
        UserLike userLike = new UserLike();
        userLike.setUserId(userId);
        userLike.setWorkId(workId);
        if(this.save(userLike)){
            QueryWrapper queryWrapper = new QueryWrapper();
            queryWrapper.eq("work_id",workId);
            Works works = worksMapper.selectOne(queryWrapper);
            works.setWorkLike(works.getWorkLike()+1);
            if(worksMapper.update(works,queryWrapper)!=1){
                throw new IllegalArgumentException();
            }
        }else{
            throw new IllegalArgumentException();
        }
    }

    @Override
    @Transactional(rollbackFor = IllegalArgumentException.class)
    public void removeLike(String workId, String userId) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("work_id",workId);
        queryWrapper.eq("user_id",userId);
        if(this.remove(queryWrapper)){
            QueryWrapper queryWrapper2 = new QueryWrapper();
            queryWrapper2.eq("work_id",workId);
            Works works = worksMapper.selectOne(queryWrapper2);
            works.setWorkLike(works.getWorkLike()-1);
            if(worksMapper.update(works,queryWrapper)!=1){
                throw new IllegalArgumentException();
            }
        }else{
            throw new IllegalArgumentException();
        }

    }

    @Override
    public Integer ifWorkLike(String workId, String userId) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("work_id",workId);
        queryWrapper.eq("user_id",userId);
        UserLike userLike = this.getOne(queryWrapper);
        if(userLike == null){
            return 0;
        }else{
            return 1;
        }
    }
}
