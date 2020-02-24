package priv.markingxs.mpic.works_comments.service;

import priv.markingxs.mpic.works_comments.entity.WorksComments;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 作品评论信息记录表 服务类
 * </p>
 *
 * @author MARKinGxS
 * @since 2020-01-29
 */
public interface IWorksCommentsService extends IService<WorksComments> {

    List<WorksComments> getWorkAllComment(String workId);

    Boolean addComment(String workId,String userEmail,String fatherCommentId,String content);
}
