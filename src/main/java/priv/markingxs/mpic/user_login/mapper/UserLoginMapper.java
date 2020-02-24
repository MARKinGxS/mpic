package priv.markingxs.mpic.user_login.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import priv.markingxs.mpic.user_login.entity.UserLogin;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 用户登录信息表 Mapper 接口
 * </p>
 *
 * @author MARKinGxS
 * @since 2020-01-29
 */

@Mapper
public interface UserLoginMapper extends BaseMapper<UserLogin> {

    @Select("select * from user_login")
    List<UserLogin> selectAllLoginInfo();


}
