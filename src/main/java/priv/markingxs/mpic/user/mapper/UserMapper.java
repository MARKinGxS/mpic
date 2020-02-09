package priv.markingxs.mpic.user.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import priv.markingxs.mpic.user.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 用户表，存储用户信息（除用户登录信息） Mapper 接口
 * </p>
 *
 * @author MARKinGxS
 * @since 2020-02-07
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

    @Select("select * from user where user_id = #{userid,jdbcType=VARCHAR}")
    User getUserForUserID(@Param("userid") String userid);
}
