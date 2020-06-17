package com.atguan.scw.order.service.exp.hander;

import com.atguan.scw.order.service.TProjectFeign;
import com.atguan.scw.order.vo.resp.TReturn;
import com.atguan.scw.vo.resp.AppResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class TProjectFeignExpectionHander implements TProjectFeign {
    @Override
    public AppResponse<TReturn> returnInfo(Integer returnId) {
        AppResponse<TReturn> resp = AppResponse.fail(null);
        resp.setMsg("调用远程服务【查询回报】失败");

        log.error("调用远程服务【查询回报】失败");

        return resp;
    }
}
