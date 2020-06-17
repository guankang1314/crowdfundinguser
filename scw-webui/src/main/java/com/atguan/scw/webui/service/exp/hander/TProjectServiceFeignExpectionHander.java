package com.atguan.scw.webui.service.exp.hander;

import com.atguan.scw.vo.resp.AppResponse;
import com.atguan.scw.webui.service.TProjectServiceFeign;
import com.atguan.scw.webui.vo.resp.ProjectVo;
import com.atguan.scw.webui.vo.resp.ProjectVoDetail;
import com.atguan.scw.webui.vo.resp.ReturnPayConfirmVo;
import com.google.inject.internal.asm.$Attribute;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class TProjectServiceFeignExpectionHander implements TProjectServiceFeign {
    @Override
    public AppResponse<List<ProjectVo>> all() {
        AppResponse<List<ProjectVo>> resp = AppResponse.fail(null);

        resp.setMsg("调用远程服务【查询首页热点项目】失败");

        log.debug("调用远程服务查询热点项目失败");

        return resp;
    }

    @Override
    public AppResponse<ProjectVoDetail> detailsInfo(Integer projectId) {
        AppResponse<ProjectVoDetail> response = AppResponse.fail(null);

        response.setMsg("调用远程服务【查询项目详情】失败");

        log.debug("调用远程服务查询项目详情失败");

        return response;
    }

    @Override
    public AppResponse<ReturnPayConfirmVo> returnInfo(Integer projectId, Integer returnId) {

        AppResponse<ReturnPayConfirmVo> resp = AppResponse.fail(null);

        resp.setMsg("调用远程服务【查询回报】失败");

        log.debug("调用远程服务查询回报详情失败");

        return resp;
    }
}
