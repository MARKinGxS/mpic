package priv.markingxs.mpic.works_comments.mapper;

import org.apache.ibatis.annotations.Mapper;
import priv.markingxs.mpic.works_comments.entity.WorksComments;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 作品评论信息记录表 Mapper 接口
 * </p>
 *
 * @author MARKinGxS
 * @since 2020-01-29
 */

@Mapper
public interface WorksCommentsMapper extends BaseMapper<WorksComments> {

}
