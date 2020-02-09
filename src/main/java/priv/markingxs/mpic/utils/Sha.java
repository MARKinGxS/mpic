package priv.markingxs.mpic.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

//密码加密工具类
public class Sha {

    private static Logger logger = LoggerFactory.getLogger(new Sha().getClass());

    public static String SHA512(String password){
        return sha(password,"SHA");
    }

    //SHA加密工具
    private static String sha(String password,String strType){
        String strResult = null;
        if(password != null && password.length()>0){
            try{
                MessageDigest messageDigest = MessageDigest.getInstance(strType);
                messageDigest.update(password.getBytes());
                byte[] byteBuffer = messageDigest.digest();
                StringBuffer strHexString = new StringBuffer();
                for(int i= 0;i<byteBuffer.length;i++){
                    String hex = Integer.toHexString(0xff & byteBuffer[i]);
                    if(hex.length() == 1){
                        strHexString.append('0');
                    }
                    strHexString.append(hex);
                }
                strResult = strHexString.toString();
            }
            catch (NoSuchAlgorithmException e){
                logger.error("-["+new Date() +"]-[密码使用 SHA 加密异常]- "+e.getMessage());
                e.printStackTrace();
            }
        }
        return strResult;
    }

}
