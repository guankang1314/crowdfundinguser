package com.atguan.scw.order.service;

import com.atguan.scw.order.bean.TOrder;
import com.atguan.scw.order.vo.req.OrderInfoSubmitVo;

public interface orderService {
    TOrder saveOrder(OrderInfoSubmitVo orderInfoSubmitVo);
}
