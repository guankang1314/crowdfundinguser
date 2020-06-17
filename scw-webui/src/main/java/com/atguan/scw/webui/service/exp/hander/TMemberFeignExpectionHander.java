package com.atguan.scw.webui.service.exp.hander;

import com.atguan.scw.vo.resp.AppResponse;
import com.atguan.scw.webui.service.TMemberFeign;
import com.atguan.scw.webui.vo.resp.UserAddressVo;
import com.atguan.scw.webui.vo.resp.UserRespVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;


@Slf4j
@Component
public class TMemberFeignExpectionHander implements TMemberFeign {


    @Override
    public AppResponse<UserRespVo> login(String loginacct, String password) {
        AppResponse<UserRespVo> resp = AppResponse.fail(null);
        resp.setMsg("调用远程服务【登录】失败");

        log.error("调用远程服务【登录】失败");

        return resp;
    }

    @Override
    public AppResponse<List<UserAddressVo>> address(String accessToken) {

        AppResponse<List<UserAddressVo>> response = AppResponse.fail(null);

        response.setMsg("调用远程服务【查询用户地址】失败");

        log.error("调用远程服务【查询用户地址】失败");

        return response;
    }
}
