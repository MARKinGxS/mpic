package priv.markingxs.mpic.works.entity;

import lombok.Data;

import java.util.List;

/**
 * 作品分页获取对象
 */

@Data
public class WorksPage {
    private Integer current;
    private Integer size;
    private Long total;
    private List<Works> worksList;

}