package priv.markingxs.mpic.works.service.impl;

import priv.markingxs.mpic.works.entity.Works;
import priv.markingxs.mpic.works.mapper.WorksMapper;
import priv.markingxs.mpic.works.service.IWorksService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 作品信息记录表 服务实现类
 * </p>
 *
 * @author MARKinGxS
 * @since 2020-02-03
 */
@Service
public class WorksServiceImpl extends ServiceImpl<WorksMapper, Works> implements IWorksService {

}
