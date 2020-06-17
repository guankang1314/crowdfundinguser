package com.atguan.scw.webui.service;


import com.atguan.scw.vo.resp.AppResponse;
import com.atguan.scw.webui.service.exp.hander.TOrderServiceFeignExpectionHander;
import com.atguan.scw.webui.vo.req.OrderInfoSubmitVo;
import com.atguan.scw.webui.vo.resp.TOrder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(value = "SCW-ORDER",fallback = TOrderServiceFeignExpectionHander.class )
public interface TOrderServiceFeign {


    @RequestMapping("/order/saveOrder")
    AppResponse<TOrder> saveOrder(@RequestBody OrderInfoSubmitVo orderInfoSubmitVo);
}
