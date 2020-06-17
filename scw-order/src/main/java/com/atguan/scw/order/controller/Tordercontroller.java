package com.atguan.scw.order.controller;

import com.atguan.scw.order.bean.TOrder;
import com.atguan.scw.order.mapper.TOrderMapper;
import com.atguan.scw.order.service.orderService;
import com.atguan.scw.order.vo.req.OrderInfoSubmitVo;
import com.atguan.scw.vo.resp.AppResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class Tordercontroller {

    @Autowired
    orderService orderService;


    @ResponseBody
    @RequestMapping("/order/saveOrder")
    public AppResponse<TOrder> saveOrder(@RequestBody OrderInfoSubmitVo orderInfoSubmitVo) {


        try {

            log.debug("保存订单开始-{}",orderInfoSubmitVo);
            TOrder order = orderService.saveOrder(orderInfoSubmitVo);


            AppResponse<TOrder> resp = AppResponse.ok(order);

            return resp;
        } catch (Exception e) {
            e.printStackTrace();
            AppResponse<TOrder> resp = AppResponse.fail(null);

            resp.setMsg("保存订单失败");

            log.error("保存订单失败");
            return resp;
        }

    }
}
