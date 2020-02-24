package priv.markingxs.mpic.works.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;
import priv.markingxs.mpic.base.responseBase.entity.ResponseBase;
import priv.markingxs.mpic.base.responseBase.service.BaseResponseApiService;
import priv.markingxs.mpic.user_login.entity.UserLogin;
import priv.markingxs.mpic.user_login.service.IUserLoginService;
import priv.markingxs.mpic.utils.FileUtil;
import priv.markingxs.mpic.utils.IDGenerator;
import priv.markingxs.mpic.works.entity.Works;
import priv.markingxs.mpic.works.service.IWorksService;
import priv.markingxs.mpic.works.entity.WorksPage;
import priv.markingxs.mpic.works.service.WorksRankingToRedis;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 作品信息记录表 前端控制器
 * </p>
 *
 * @author MARKinGxS
 * @since 2020-02-21
 */
@RestController
@RequestMapping("/works/works")
public class WorksController {

    @Value("${imageUpload.localPath}")
    private String localPath;
    @Autowired
    @Qualifier("workService")
    private IWorksService iWorksService;
    @Autowired
    @Qualifier("userLoginservice")
    private IUserLoginService iUserLoginService;
    @Autowired
    private WorksRankingToRedis worksRankingToRedis;

    //图片上传测试
    @RequestMapping(value = "/imageUpload", method = RequestMethod.POST)
    public Map<String,Object> imageUpload(@RequestParam("fileName") MultipartFile file,HttpServletRequest request){


        String result_msg = ""; //上传结果信息

        Map<String,Object> root = new HashMap<>();


        String username = (String)request.getParameter("username");
        System.out.println(username+"!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");

        String useremail = (String)request.getHeader("useremail");
        System.out.println(useremail+"~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");

        if((file.getSize()/1000)>100){
            result_msg = "Picture's size is bigger than 100KB";
        }else{
            //判断上传文件格式
            String fileType = file.getContentType();
            if(fileType.equals("image/jpg") || fileType.equals("image/png") || fileType.equals("image/jpeg")) {
                final String localPath = this.localPath;
                //获取文件名
                String fileName = file.getOriginalFilename();
                //获取文件后缀名
                String suffixName = fileName.substring(fileName.lastIndexOf("."));
                //重新生成后缀名
                fileName = IDGenerator.workIdGenerate()+suffixName;

                if(FileUtil.upload(file,localPath,fileName)!=null) {
                    String relativePath = "img/" + fileName;
                    root.put("relativePath", relativePath);
                    result_msg = "图片上传成功";
                }else{
                    result_msg = "图片上传失败";
                }
            }else{
                result_msg = "图片格式不正确";
            }

        }
        root.put("result_msg",result_msg);
        return root;
    }

    //分页获取作品
    @RequestMapping(value = "/getPersonalPageWorks",method = RequestMethod.GET)
    public ResponseBase getPersonalPageWorks(@RequestParam("pageIndex") Integer pageIndex){
        WorksPage worksPage = iWorksService.queryWorksForPage(pageIndex,36);
        ResponseBase responseBase = null;
        if(worksPage!=null){
            responseBase = BaseResponseApiService.customResult(20000,"success works page",worksPage);
        }
        return responseBase;
    }

    //作品发布
    // 60001作品名称为空或空格或换行符等
    // 60002作品发布失败
    // 60003作品格式不正确
    // 60004作品上传失败
    @RequestMapping(value = "/publishWork",method = RequestMethod.POST)
    public ResponseBase publishWork(@RequestParam("fileName") MultipartFile file,HttpServletRequest request){

        ResponseBase responseBase = null;

        String workName = (String)request.getParameter("workname");

        String fileType = file.getContentType();
        if(fileType.equals("image/jpg") || fileType.equals("image/png") || fileType.equals("image/jpeg")) {
            if (StringUtils.isBlank(workName)) {
                responseBase = BaseResponseApiService.customResult(60001, "Work's name is wrong data", null);
            } else {
                //MultipartFile file = (MultipartFile) workMap.get("fileName");
                String userEmail = request.getHeader("useremail");
                QueryWrapper<UserLogin> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("user_email", userEmail);
                String userId = iUserLoginService.getOne(queryWrapper).getUserId();
                String workDescribe = (String)request.getParameter("workcontent");
                Integer code = iWorksService.saveWorks(workName, userId, workDescribe, file);
                switch (code){
                    case 20000:
                        responseBase = BaseResponseApiService.successResultNoData();
                    case 60004:
                        responseBase = BaseResponseApiService.customResult(60004, "Upload work is failed", null);
                    case 60002:
                        responseBase = BaseResponseApiService.customResult(60002, "Publish work is failed", null);
                }
            }
        }else{
            responseBase =BaseResponseApiService.customResult(60003,"作品格式不正确",null);
        }
        return responseBase;

    }

    //获取单个作品
    @RequestMapping(value = "/getSingleWork", method = RequestMethod.GET)
    public ResponseBase getSingleWork(@RequestParam("workId") String workId ){
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("work_id",workId);
        Works works = iWorksService.getOne(queryWrapper);
        return BaseResponseApiService.successResult(works);
    }



    //作品排行榜，只需要前十个
    @RequestMapping(value = "/getWorksRanking",method = RequestMethod.GET)
    public ResponseBase getWorksRanking(){
        List<Works> worksList = worksRankingToRedis.getRankingListFromRedis("worksRanking");
        if (worksList != null) {
            return BaseResponseApiService.successResult(worksList);
        }else{
            worksList = iWorksService.sortAndGetWorksList();
            worksRankingToRedis.worksRankingToRedis("worksRanking",worksList);
            return BaseResponseApiService.successResult(worksList);
        }
    }


}

