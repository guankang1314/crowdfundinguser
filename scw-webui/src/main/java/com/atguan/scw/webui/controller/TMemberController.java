package com.atguan.scw.webui.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Slf4j
@Controller
public class TMemberController {


    @RequestMapping("member/minecrowdfunding")
    public String myOrderList() {

        log.debug("支付后,同步请求通知处理...");

        return "member/minecrowdfunding";
    }

}
