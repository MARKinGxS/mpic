package priv.markingxs.mpic.works_comments.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import priv.markingxs.mpic.user_login.entity.UserLogin;
import priv.markingxs.mpic.user_login.service.IUserLoginService;
import priv.markingxs.mpic.utils.IDGenerator;
import priv.markingxs.mpic.works_comments.entity.WorksComments;
import priv.markingxs.mpic.works_comments.mapper.WorksCommentsMapper;
import priv.markingxs.mpic.works_comments.service.IWorksCommentsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 作品评论信息记录表 服务实现类
 * </p>
 *
 * @author MARKinGxS
 * @since 2020-01-29
 */
@Service("workCommentsService")
public class WorksCommentsServiceImpl extends ServiceImpl<WorksCommentsMapper, WorksComments> implements IWorksCommentsService {

    @Autowired
    private WorksCommentsMapper worksCommentsMapper;
    @Autowired
    @Qualifier("userLoginservice")
    private IUserLoginService iUserLoginService;

    @Override
    public List<WorksComments> getWorkAllComment(String workId) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("work_id",workId);
        return worksCommentsMapper.selectList(queryWrapper);
    }

    @Override
    public Boolean addComment(String workId, String userEmail, String fatherCommentId, String content) {
        QueryWrapper<UserLogin> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_email", userEmail);
        String userId = iUserLoginService.getOne(queryWrapper).getUserId();

        String commentId = IDGenerator.commentIdGenerate();

        WorksComments worksComments = new WorksComments();
        worksComments.setCommentId(commentId);
        worksComments.setWorkId(workId);
        worksComments.setUserId(userId);
        if(!(StringUtils.isBlank(fatherCommentId)||fatherCommentId==null)){
            worksComments.setCommentFatherId(fatherCommentId);
        }
        worksComments.setUserEmail(userEmail);
        worksComments.setCommentContent(content);
        return this.save(worksComments);
    }
}
