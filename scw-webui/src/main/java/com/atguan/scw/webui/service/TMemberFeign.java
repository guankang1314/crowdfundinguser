package com.atguan.scw.webui.service;


import com.atguan.scw.vo.resp.AppResponse;
import com.atguan.scw.webui.service.exp.hander.TMemberFeignExpectionHander;
import com.atguan.scw.webui.vo.resp.UserAddressVo;
import com.atguan.scw.webui.vo.resp.UserRespVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(value = "SCW-USER",fallback = TMemberFeignExpectionHander.class)
public interface TMemberFeign {

    @PostMapping("/user/login")
    public AppResponse<UserRespVo> login(@RequestParam("loginacct") String loginacct,@RequestParam("password") String password);


    @GetMapping("/user/info/address")
    public AppResponse<List<UserAddressVo>> address(@RequestParam("accessToken") String accessToken);
}
