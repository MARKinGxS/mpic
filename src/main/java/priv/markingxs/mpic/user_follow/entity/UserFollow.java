package priv.markingxs.mpic.user_follow.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 用户关注用户记录表
 * </p>
 *
 * @author MARKinGxS
 * @since 2020-01-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class UserFollow implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 唯一，自增
     */
    @TableId(value = "system_id", type = IdType.AUTO)
    private Integer systemId;

    /**
     * 用户id（年月日时分秒+随机五位数+u）组成
     */
    private String userId;

    /**
     * 被关注用户id（年月日时分秒+随机五位数+u）组成
     */
    private String userFollowId;


}
