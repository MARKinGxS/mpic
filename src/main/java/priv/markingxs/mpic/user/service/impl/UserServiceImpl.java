package priv.markingxs.mpic.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;
import priv.markingxs.mpic.user.entity.User;
import priv.markingxs.mpic.user.mapper.UserMapper;
import priv.markingxs.mpic.user.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import priv.markingxs.mpic.user_login.service.IUserLoginService;
import priv.markingxs.mpic.utils.FileUtil;
import priv.markingxs.mpic.utils.IDGenerator;

/**
 * <p>
 * 用户表，存储用户信息（除用户登录信息） 服务实现类
 * </p>
 *
 * @author MARKinGxS
 * @since 2020-02-07
 */
@Service("userservice")
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    @Qualifier("userLoginservice")
    private IUserLoginService iUserLoginService;
    @Value("${headImageUpload.localPath}")
    private String headImgLocalPath;

    public User getUserForUserID(String userid){
        return userMapper.getUserForUserID(userid);
    }

    @Override
    public Boolean saveUserInfo(String userEmail, String userName, MultipartFile file) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("user_email",userEmail);
        String userId = iUserLoginService.getOne(queryWrapper).getUserId();

        String headImgId = IDGenerator.headImgIdGenerate();
        final String headImgLocalPath = this.headImgLocalPath;
        String fileName = file.getOriginalFilename();
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        fileName = headImgId + suffixName;
        String realPath = FileUtil.upload(file,headImgLocalPath,fileName);

        User user = this.getUserForUserID(userId);
        if(user == null){
            user = new User();
            user.setUserId(userId);
            user.setUserCharacter(0);
            this.save(user);
        }
        user.setUserName(userName);
        user.setUserImg(realPath);
        QueryWrapper queryWrapper2 = new QueryWrapper();
        queryWrapper2.eq("user_id",userId);
        return this.update(user,queryWrapper2);
    }

}
