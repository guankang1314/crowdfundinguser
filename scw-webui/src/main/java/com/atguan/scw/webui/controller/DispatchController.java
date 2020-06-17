package com.atguan.scw.webui.controller;


import com.alibaba.fastjson.JSON;
import com.atguan.scw.vo.resp.AppResponse;
import com.atguan.scw.webui.service.TMemberFeign;
import com.atguan.scw.webui.service.TProjectServiceFeign;
import com.atguan.scw.webui.vo.resp.ProjectVo;
import com.atguan.scw.webui.vo.resp.UserRespVo;
import jdk.nashorn.internal.runtime.JSONFunctions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Controller
public class DispatchController{


    @Autowired
    TMemberFeign memberFeign;

    @Autowired
    TProjectServiceFeign projectServiceFeign;

    @Autowired
    RedisTemplate redisTemplate;


    @RequestMapping("/index")
    public String index(Model model) {

        List<ProjectVo> data  = (List<ProjectVo>) redisTemplate.opsForValue().get("projectInfo");

        if (data==null) {

            AppResponse<List<ProjectVo>> resp = projectServiceFeign.all();

            data = resp.getData();

            redisTemplate.opsForValue().set("data",data,1, TimeUnit.HOURS);
        }

        model.addAttribute("projectVoList",data);

        return "index";
    }

    @RequestMapping("/login")
    public String login() {

        return "login";
    }

    @RequestMapping("/dologin")
    public String dologin(String loginacct, String userpswd, HttpSession session) {

        AppResponse<UserRespVo> resp = memberFeign.login(loginacct, userpswd);

        UserRespVo data = resp.getData();

        if (data==null) {
            return "login";
        }

        session.setAttribute("loginMember",data);

        String preUrl = (String) session.getAttribute("preUrl");

        if (StringUtils.isEmpty(preUrl)) {
            return "redirect:/index";

        }else {
            return "redirect:"+preUrl;
        }

    }


    @RequestMapping("/logout")
    public String logout(HttpSession session) {

        if (session!=null) {
            session.removeAttribute("loginMember");
            session.invalidate();
        }

        return "redirect:/index";
    }


}
