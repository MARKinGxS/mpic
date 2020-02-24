package priv.markingxs.mpic.user.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 用户表，存储用户信息（除用户登录信息）
 * </p>
 *
 * @author MARKinGxS
 * @since 2020-02-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class User implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 唯一，自增
     */
    @TableId(value = "system_id", type = IdType.AUTO)
    private Integer systemId;

    /**
     * 使用（年月日时分秒+随机五位数+u）组成
     */
    private String userId;

    /**
     * 用户页面显示的昵称
     */
    private String userName;

    /**
     * 用户头像路径，若为null则指向系统默认头像图片
     */
    private String userImg;

    /**
     * 用户角色，若为管理员则为1，否则为0
     */
    private Integer userCharacter;


}
