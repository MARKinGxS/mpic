package priv.markingxs.mpic.works.mapper;

import org.apache.ibatis.annotations.Mapper;
import priv.markingxs.mpic.works.entity.Works;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 作品信息记录表 Mapper 接口
 * </p>
 *
 * @author MARKinGxS
 * @since 2020-02-03
 */

@Mapper
public interface WorksMapper extends BaseMapper<Works> {

}
