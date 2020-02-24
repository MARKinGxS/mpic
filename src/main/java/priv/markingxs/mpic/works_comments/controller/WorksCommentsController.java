package priv.markingxs.mpic.works_comments.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import priv.markingxs.mpic.base.responseBase.entity.ResponseBase;
import priv.markingxs.mpic.base.responseBase.service.BaseResponseApiService;
import priv.markingxs.mpic.user.entity.User;
import priv.markingxs.mpic.user.service.IUserService;
import priv.markingxs.mpic.user_login.entity.UserLogin;
import priv.markingxs.mpic.user_login.service.IUserLoginService;
import priv.markingxs.mpic.works_comments.entity.WorksComments;
import priv.markingxs.mpic.works_comments.service.IWorksCommentsService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 作品评论信息记录表 前端控制器
 * </p>
 *
 * @author MARKinGxS
 * @since 2020-01-29
 */
@RestController
@RequestMapping("/works_comments/works-comments")
public class WorksCommentsController {

    @Autowired
    @Qualifier("workCommentsService")
    private IWorksCommentsService iWorksCommentsService;
    @Autowired
    @Qualifier("userservice")
    private IUserService iUserService;


    //获取该作品所有的评论
    @RequestMapping(value = "/getWorkAllComment", method = RequestMethod.GET)
    public ResponseBase getWorkAllComment(@RequestParam("workId") String workId){
        List<WorksComments> commentsList = iWorksCommentsService.getWorkAllComment(workId);
        for(WorksComments worksComments:commentsList){
            User user = iUserService.getUserForUserID(worksComments.getUserId());
            String userName = user.getUserName();
            if(userName!=null){
                worksComments.setUserEmail(userName);
            }
        }
        return BaseResponseApiService.customResult(20000,"获取作品评论成功",commentsList);
    }

    //用户评论作品
    @RequestMapping(value = "/addWorkComment", method = RequestMethod.POST)
    public ResponseBase addWorkComment(@RequestParam("workId")String workId, @RequestBody Map<String,Object> infoMap, HttpServletRequest request){
        String userEmail = request.getHeader("useremail");
        String fatherCommentId = (String)infoMap.get("fatherCommentId");
        String content = (String)infoMap.get("content");
        if(iWorksCommentsService.addComment(workId,userEmail,fatherCommentId,content)){
            return BaseResponseApiService.customResult(20000,"添加评论成功",null);
        }else{
            return BaseResponseApiService.customResult(90001,"添加评论失败",null);
        }
    }

}

