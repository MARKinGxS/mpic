package priv.markingxs.mpic.works_comments.entity;

import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 作品评论信息记录表
 * </p>
 *
 * @author MARKinGxS
 * @since 2020-01-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class WorksComments implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 评论id（年月日时分秒+随机五位数+c）组成
     */
    private String commentId;

    /**
     * 被评论的作品id
     */
    private String workId;

    /**
     * 评论的用户id
     */
    private String userId;

    /**
     * 父级评论id（若为null，则为父级评论id）
     */
    private String commentFatherId;


}
