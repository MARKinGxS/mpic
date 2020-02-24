package priv.markingxs.mpic.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class IDGenerator {

    public static String userIdGenerate(){
        return idMaker("u");
    }

    public static String workIdGenerate(){
        return idMaker("w");
    }

    public static String commentIdGenerate(){
        return idMaker("c");
    }

    public static String headImgIdGenerate(){
        return idMaker("h");
    }

    private static String idMaker(String identifier){
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String strTime = simpleDateFormat.format(date);
        String randomNum = String.valueOf((int)((Math.random()*9+1)*10000));
        return strTime+randomNum+identifier;
    }



}
