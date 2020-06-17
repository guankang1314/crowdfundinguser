package com.atguan.scw.order.service;

import com.atguan.scw.order.service.exp.hander.TProjectFeignExpectionHander;
import com.atguan.scw.order.vo.resp.TReturn;
import com.atguan.scw.vo.resp.AppResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "SCW-PROJECT",fallback = TProjectFeignExpectionHander.class)
public interface TProjectFeign {

    @GetMapping("/project/details/returns/info/{returnId}")
    public AppResponse<TReturn> returnInfo(@PathVariable("returnId") Integer returnId);
}
