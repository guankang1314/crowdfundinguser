package com.atguan.scw.project.vo.req;


import com.atguan.scw.project.bean.TReturn;
import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@ToString
@Data
public class ProjectRedisStorageVo extends BaseVo {


    /**
     * 项目的临时token
     */
    private String projectToken;

    private List<Integer> typeids = new ArrayList<Integer>(); //项目的分类id
    private List<Integer> tagids = new ArrayList<Integer>(); //项目的标签id

    private String name;//项目名称
    private String remark;//项目简介
    private Integer money;//筹资金额
    private Integer day;//筹资天数

    private String headerImage;//项目头部图片
    private List<String> detailsImage = new ArrayList<String>();//项目详情图片

    private List<TReturn> projectReturns = new ArrayList<TReturn>();//项目回报

    //发起人信息：自我介绍，详细自我介绍，联系电话，客服电话

}
