package com.atguan.scw.project.service.exp.hander;

import com.atguan.scw.project.service.MemberServiceFeign;
import com.atguan.scw.project.vo.resp.TMember;
import com.atguan.scw.vo.resp.AppResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MemberServiceFeignExpectionHander implements MemberServiceFeign {
    @Override
    public AppResponse<TMember> getMebmerById(Integer id) {

        AppResponse<TMember> resp = AppResponse.fail(null);

        resp.setMsg("调用远程服务【查询用户】失败");

        log.debug("调用远程服务用户查询失败");

        return resp;
    }
}
