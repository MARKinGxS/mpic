package priv.markingxs.mpic.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Date;

public class FileUtil {


    private static Logger logger = LoggerFactory.getLogger(new FileUtil().getClass());
    /**
     * @param file     文件
     * @param path     文件存放路径
     * @param fileName 保存的文件名
     * @return
     */
    public static String upload(MultipartFile file, String path, String fileName){
        return uploadFile(file,path,fileName);
    }

    private static String uploadFile(MultipartFile file, String path, String fileName) {

        String realPath = path + "\\" + fileName;

        File dest = new File(realPath);
        //查看文件父目录是否存在，不存在则创建
        if(!dest.getParentFile().exists()){
            dest.getParentFile().mkdir();
        }

        try{
            file.transferTo(dest);
            logger.info("-["+ new Date() +"]- upload file success in path:"+realPath);
            return realPath;
        }catch (IllegalStateException e){
            logger.error("-["+ new Date() +"]- upload file fail");
            e.printStackTrace();
            return null;
        }catch (IOException e){
            logger.error("-["+ new Date() +"]- upload file fail");
            e.printStackTrace();
            return null;
        }
    }
}
