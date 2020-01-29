package priv.markingxs.mpic.user_login.entity;

import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 用户登录信息表
 * </p>
 *
 * @author MARKinGxS
 * @since 2020-01-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class UserLogin implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 用户id（年月日时分秒+随机五位数+u）组成
     */
    private String userId;

    /**
     * 用户邮箱号
     */
    private String userEmail;

    /**
     * 用户登陆密码
     */
    private String userPassword;


}
