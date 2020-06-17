package com.atguan.scw.webui.vo.resp;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@ToString
@Data
public class ProjectVoDetail implements Serializable {
	
	private Integer memberid;// 会员id

//	//第二步：收集项目基本信息以及发起人信息（暂时不做）
//	private List<Integer> typeids; // 项目的分类id
//	private List<Integer> tagids; // 项目的标签id

	private Integer id;

	private String name;

	private String remark;

	private Long money;

	private Integer day;

	private String status;

	private String deploydate;

	private Long supportmoney=0L;

	private Integer supporter=0;

	private Integer completion=0;


	private Integer follower = 100;
	
	private String headerImage;// 项目头部图片

	private List<String> detailsImage = new ArrayList<String>();// 项目详情图片
	// 发起人信息：自我介绍，详细自我介绍，联系电话，客服电话

	//第三步：收集回报信息
	private List<TReturn> projectReturns = new ArrayList<TReturn>();// 项目回报

}
