package com.atguan.scw.project.service;


import com.atguan.scw.project.service.exp.hander.MemberServiceFeignExpectionHander;
import com.atguan.scw.project.vo.resp.TMember;
import com.atguan.scw.vo.resp.AppResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "SCW-USER",fallback = MemberServiceFeignExpectionHander.class)
public interface MemberServiceFeign {

    @GetMapping("/user/info/getMebmerById")
    public AppResponse<TMember> getMebmerById(@RequestParam("id") Integer id);
}
