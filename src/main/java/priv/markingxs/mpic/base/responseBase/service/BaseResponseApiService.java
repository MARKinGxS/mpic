package priv.markingxs.mpic.base.responseBase.service;

import priv.markingxs.mpic.base.responseBase.entity.ResponseBase;

public class BaseResponseApiService {

    private static ResponseBase setResult(Integer code, String msg, Object data){
        return new ResponseBase(code,msg,data);
    }

    //请求成功且有数据
    public static ResponseBase successResult(Object data){
        return setResult(20000,"require success",data);
    }

    //请求成功但无数据
    public static ResponseBase successResultNoData(){
        return setResult(20000,"require success",null);
    }

    //自定义返回对象
    public static ResponseBase customResult(Integer code, String msg, Object data){
        return setResult(code,msg,data);
    }


}
