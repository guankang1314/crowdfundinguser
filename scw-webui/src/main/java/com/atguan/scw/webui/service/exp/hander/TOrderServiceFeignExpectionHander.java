package com.atguan.scw.webui.service.exp.hander;

import com.atguan.scw.vo.resp.AppResponse;
import com.atguan.scw.webui.service.TOrderServiceFeign;
import com.atguan.scw.webui.vo.req.OrderInfoSubmitVo;
import com.atguan.scw.webui.vo.resp.ProjectVoDetail;
import com.atguan.scw.webui.vo.resp.TOrder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class TOrderServiceFeignExpectionHander implements TOrderServiceFeign {
    @Override
    public AppResponse<TOrder> saveOrder(OrderInfoSubmitVo orderInfoSubmitVo) {
        AppResponse<TOrder> response = AppResponse.fail(null);

        response.setMsg("调用远程服务【下订单】失败");

        log.debug("调用远程服务下订单失败");

        return response;
    }
}
