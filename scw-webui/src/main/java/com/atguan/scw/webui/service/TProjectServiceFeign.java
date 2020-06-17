package com.atguan.scw.webui.service;


import com.atguan.scw.vo.resp.AppResponse;
import com.atguan.scw.webui.service.exp.hander.TProjectServiceFeignExpectionHander;
import com.atguan.scw.webui.vo.resp.ProjectVo;
import com.atguan.scw.webui.vo.resp.ProjectVoDetail;
import com.atguan.scw.webui.vo.resp.ReturnPayConfirmVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(value = "SCW-PROJECT",fallback = TProjectServiceFeignExpectionHander.class)
public interface TProjectServiceFeign {

    @GetMapping("/project/all")
    public AppResponse<List<ProjectVo>> all();

    @GetMapping("/project/details/info/{projectId}")
    public AppResponse<ProjectVoDetail> detailsInfo(@PathVariable("projectId") Integer projectId);

    @GetMapping("/project/confim/returns/{projectId}/{returnId}")
    public AppResponse<ReturnPayConfirmVo> returnInfo(@PathVariable("projectId") Integer projectId,
                                                      @PathVariable("returnId") Integer returnId);

}
