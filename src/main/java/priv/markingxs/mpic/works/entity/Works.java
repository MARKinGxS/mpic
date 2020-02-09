package priv.markingxs.mpic.works.entity;

import java.time.LocalDate;
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
 * @since 2020-02-03
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
    private LocalDate workUploadTime;

    /**
     * 作品审核步骤，0为进入一层审核，1为进入第二层审核，2为通过审核，-1为未通过
     */
    private Integer workVerifyStep;


}
