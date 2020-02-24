package priv.markingxs.mpic.works.service;

import org.springframework.web.multipart.MultipartFile;
import priv.markingxs.mpic.works.entity.Works;
import com.baomidou.mybatisplus.extension.service.IService;
import priv.markingxs.mpic.works.entity.WorksPage;

import java.util.List;

/**
 * <p>
 * 作品信息记录表 服务类
 * </p>
 *
 * @author MARKinGxS
 * @since 2020-02-21
 */
public interface IWorksService extends IService<Works> {

    WorksPage queryWorksForPage(Integer pageIndex,Integer pageSize);

    Integer saveWorks(String workName, String userId,String workDescribe, MultipartFile file);

    List<Works> sortAndGetWorksList();

}
