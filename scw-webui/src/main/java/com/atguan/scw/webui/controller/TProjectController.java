package com.atguan.scw.webui.controller;


import com.atguan.scw.vo.resp.AppResponse;
import com.atguan.scw.webui.service.TMemberFeign;
import com.atguan.scw.webui.service.TProjectServiceFeign;
import com.atguan.scw.webui.vo.resp.*;
import freemarker.ext.beans.StringModel;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.List;

@Controller
public class TProjectController {

    @Autowired
    TProjectServiceFeign projectServiceFeign;

    @Autowired
    TMemberFeign memberFeign;


    @RequestMapping("/project/projectInfo")
    public String index(Integer id, Model model) {

        AppResponse<ProjectVoDetail> resp = projectServiceFeign.detailsInfo(id);
        ProjectVoDetail vo = resp.getData();
        model.addAttribute("ProjectVoDetail",vo);


        return "project/index";
    }

    @RequestMapping("/project/support/{projectId}/{returnId}")
    public String support(@PathVariable("projectId") Integer projectId,
                          @PathVariable("returnId") Integer returnId,Model model,HttpSession session) {

        AppResponse<ReturnPayConfirmVo> resp = projectServiceFeign.returnInfo(projectId,returnId);
        ReturnPayConfirmVo confirmVo = resp.getData();

        model.addAttribute("returnPayConfirmVo",confirmVo);

        session.setAttribute("returnPayConfirmVoSession",confirmVo);

        return "project/pay-step-1";
    }

    @RequestMapping("/project/comfirm/order/{num}")
    public String comfirmorder(@PathVariable("num") Integer num,Model model, HttpSession session) {




        //地址信息
        UserRespVo userRespVo = (UserRespVo) session.getAttribute("loginMember");

        if (userRespVo == null) {

            session.setAttribute("preUrl","/project/comfirm/order/"+num);

            return "redirect:/login";
        }

        String accessToken = userRespVo.getAccessToken();


        AppResponse<List<UserAddressVo>> resp = memberFeign.address(accessToken);

        List<UserAddressVo> memberAddressList = resp.getData();

        model.addAttribute("memberAddressList",memberAddressList);

        //汇报信息
        ReturnPayConfirmVo returnPayConfirmVo  = (ReturnPayConfirmVo) session.getAttribute("returnPayConfirmVoSession");

        returnPayConfirmVo.setNum(num);
        returnPayConfirmVo.setTotalPrice(new BigDecimal(num*returnPayConfirmVo.getPrice()+returnPayConfirmVo.getFreight()));

        session.setAttribute("returnPayConfirmVoSession",returnPayConfirmVo);


        return "project/pay-step-2";
    }


}
