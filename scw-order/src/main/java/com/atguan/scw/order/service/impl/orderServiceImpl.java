package com.atguan.scw.order.service.impl;


import com.atguan.scw.enums.OrderStatusEnumes;
import com.atguan.scw.order.bean.TOrder;
import com.atguan.scw.order.mapper.TOrderMapper;
import com.atguan.scw.order.service.TProjectFeign;
import com.atguan.scw.order.service.orderService;
import com.atguan.scw.order.vo.req.OrderInfoSubmitVo;
import com.atguan.scw.order.vo.resp.TReturn;
import com.atguan.scw.util.AppDateUtils;
import com.atguan.scw.vo.resp.AppResponse;
import com.netflix.discovery.converters.Auto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
public class orderServiceImpl implements orderService {


    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Autowired
    TOrderMapper orderMapper;

    @Autowired
    TProjectFeign projectFeign;


    @Override
    public TOrder saveOrder(OrderInfoSubmitVo orderInfoSubmitVo) {
        TOrder order = new TOrder();

        String accessToken = orderInfoSubmitVo.getAccessToken();
        String memberId = stringRedisTemplate.opsForValue().get(accessToken);

        order.setMemberid(Integer.parseInt(memberId));
        order.setProjectid(orderInfoSubmitVo.getProjectid());
        order.setReturnid(orderInfoSubmitVo.getReturnid());

        String ordernum = UUID.randomUUID().toString().replaceAll("-","");
        order.setOrdernum(ordernum);

        order.setCreatedate(AppDateUtils.getFormatTime());

        AppResponse<TReturn> response = projectFeign.returnInfo(orderInfoSubmitVo.getReturnid());
        TReturn tReturn = response.getData();


        Integer totalMoneny = tReturn.getCount()*tReturn.getSupportmoney()+tReturn.getFreight();

        order.setMoney(totalMoneny);
        order.setRtncount(orderInfoSubmitVo.getRtncount());
        order.setStatus(OrderStatusEnumes.UNPAY.getCode()+"");
        order.setAddress(orderInfoSubmitVo.getAddress());
        order.setInvoice(orderInfoSubmitVo.getInvoice().toString());
        order.setInvoictitle(orderInfoSubmitVo.getInvoictitle());
        order.setRemark(orderInfoSubmitVo.getRemark());

        orderMapper.insertSelective(order);

        log.debug("业务层保存订单-{}",order);

        return order;
    }
}
