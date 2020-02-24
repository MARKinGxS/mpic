package priv.markingxs.mpic.user_login.entity;

import lombok.Data;

import java.util.List;

/**
 * 登录信息分页获取对象
 */

@Data
public class UserLoginPage {
    private Integer current;
    private Integer size;
    private Long total;
    private List<UserLogin> userLoginList;

}
