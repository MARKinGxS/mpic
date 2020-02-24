package priv.markingxs.mpic.utils;


import java.util.UUID;

//token生成工具类
public class TokenGenerator {

    public static String tokenGenerate(){
        return tokenMaker();
    }

    private static String tokenMaker(){
        return UUID.randomUUID().toString().replaceAll("-","");
    }

}
