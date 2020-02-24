package priv.markingxs.mpic.base.responseBase.entity;

import lombok.Data;

@Data
public class ResponseBase {
    private Integer resCode;
    private String msg;
    private Object data;

    public ResponseBase(){}

    public ResponseBase(Integer resCode, String msg, Object data) {
        super();
        this.resCode = resCode;
        this.msg = msg;
        this.data = data;
    }

}
