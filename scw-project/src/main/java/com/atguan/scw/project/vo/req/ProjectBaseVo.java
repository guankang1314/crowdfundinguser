package com.atguan.scw.project.vo.req;


import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
public class ProjectBaseVo extends BaseVo {

    private String projectToken;

    /**
     * //项目的分类id//项目的标签id
     */
    private List<Integer> typeids;
    private List<Integer> tagids;

    /**
     * //项目名称//项目简介//筹资金额//筹资天数
     */
    private String name;
    private String remark;
    private Integer money;
    private Integer day;

    /**
     * //项目头部图片//项目详情图片
     */
    private String headerImage;
    private List<String> detailsImage;


}
