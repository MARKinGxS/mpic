package priv.markingxs.mpic.works.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;
import priv.markingxs.mpic.utils.FileUtil;
import priv.markingxs.mpic.utils.IDGenerator;
import priv.markingxs.mpic.works.entity.Works;
import priv.markingxs.mpic.works.entity.WorksPage;
import priv.markingxs.mpic.works.mapper.WorksMapper;
import priv.markingxs.mpic.works.service.IWorksService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * <p>
 * 作品信息记录表 服务实现类
 * </p>
 *
 * @author MARKinGxS
 * @since 2020-02-21
 */
@Service("workService")
public class WorksServiceImpl extends ServiceImpl<WorksMapper, Works> implements IWorksService {

    @Value("${imageUpload.localPath}")
    private String localPath;
    @Autowired
    private WorksMapper worksMapper;

    @Override
    public WorksPage queryWorksForPage(Integer pageIndex, Integer pageSize) {
        IPage<Works> page = new Page<Works>(pageIndex,pageSize);
        page = worksMapper.selectPage(page,null);
        WorksPage worksPage = new WorksPage();
        worksPage.setCurrent(pageIndex);
        worksPage.setSize(pageSize);
        worksPage.setTotal(page.getTotal());
        worksPage.setWorksList(page.getRecords());
        return worksPage;
    }

    @Override
    public Integer saveWorks(String workName, String userId,String workDescribe, MultipartFile file) {

        String workId = IDGenerator.workIdGenerate();

        final String localPath = this.localPath;
        String fileName = file.getOriginalFilename();
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        fileName = workId + suffixName;
        String realPath = FileUtil.upload(file,localPath,fileName);
        if(realPath == null){
            return 60004;
        }else{
            Works works = new Works();
            works.setWorkId(workId);
            works.setWorkName(workName);
            works.setWorkLike(0);
            works.setUserId(userId);
            works.setWorkUploadTime(LocalDateTime.now());
            works.setWorkUrl(realPath);
            works.setWorkDescribe(workDescribe);
            works.setWorkVerifyStep(0);
            if(worksMapper.insert(works)==1){
                return 20000;
            }else{
                return 60002;
            }
        }


    }


    @Override
    public List<Works> sortAndGetWorksList() {
        List<Works> list = worksMapper.selectList(null);
        Collections.sort(list, new Comparator<Works>() {
            @Override
            public int compare(Works o1, Works o2) {
                Integer workLike1 = o1.getWorkLike();
                Integer workLike2 = o2.getWorkLike();
                if(workLike2>workLike1){
                    return 1;
                }else if(workLike2 == workLike1){
                    return 0;
                }else{
                    return -1;
                }
            }
        });
        if(list.size()<=10){
            return list;
        }else{
            return list.subList(0,10);
        }
    }


}
