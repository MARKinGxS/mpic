package priv.markingxs.mpic.works.entity;

import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 作品信息记录表
 * </p>
 *
 * @author MARKinGxS
 * @since 2020-02-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Works implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 作品id（年月日时分秒+随机五位数+w）组成
     */
    private String workId;

    /**
     * 作品昵称
     */
    private String workName;

    /**
     * 作品获赞数目
     */
    private Integer workLike;

    /**
     * 作品上传时间
     */
    private LocalDateTime workUploadTime;

    /**
     * 作品审核步骤，0为正在审核，1为通过审核，-1为未通过
     */
    private Integer workVerifyStep;

    /**
     * 作品路径
     */
    private String workUrl;

    /**
     * 作品隶属的用户id
     */
    private String userId;

    /**
     * 作品概述
     */
    private String workDescribe;


}
