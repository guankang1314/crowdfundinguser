package com.atguan.scw.webui.controller;


import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.atguan.scw.vo.resp.AppResponse;
import com.atguan.scw.webui.config.AlipayConfig;
import com.atguan.scw.webui.service.TOrderServiceFeign;
import com.atguan.scw.webui.vo.req.OrderFormInfoSubmitVo;
import com.atguan.scw.webui.vo.req.OrderInfoSubmitVo;
import com.atguan.scw.webui.vo.resp.ReturnPayConfirmVo;
import com.atguan.scw.webui.vo.resp.TOrder;
import com.atguan.scw.webui.vo.resp.UserRespVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Slf4j
@Controller
public class TOrderController {


    @Autowired
    TOrderServiceFeign orderServiceFeign;


    @ResponseBody
    @RequestMapping("/order/pay")
    public String payOrder(OrderFormInfoSubmitVo vo, HttpSession session) {

        log.debug("提交订单-支付-{}",vo);

        //下单
        OrderInfoSubmitVo orderInfoSubmitVo = new OrderInfoSubmitVo();

        BeanUtils.copyProperties(vo,orderInfoSubmitVo);

        UserRespVo userRespVo = (UserRespVo) session.getAttribute("loginMember");

        if (userRespVo==null) {
            return "redirect:/login";
        }

        String accessToken = userRespVo.getAccessToken();
        orderInfoSubmitVo.setAccessToken(accessToken);

        ReturnPayConfirmVo returnPayConfirmVo = (ReturnPayConfirmVo) session.getAttribute("returnPayConfirmVoSession");
        if (returnPayConfirmVo==null) {
            return "redirect:/login";
        }
        orderInfoSubmitVo.setProjectid(returnPayConfirmVo.getProjectId());
        orderInfoSubmitVo.setReturnid(returnPayConfirmVo.getReturnId());
        orderInfoSubmitVo.setRtncount(returnPayConfirmVo.getNum());

        AppResponse<TOrder> resp = orderServiceFeign.saveOrder(orderInfoSubmitVo);

        TOrder order = resp.getData();

        log.debug("订单详情-{}",order);


        String orderName = returnPayConfirmVo.getProjectName();

        String result = payOrder(order.getOrdernum(), order.getMoney(),orderName,vo.getRemark());


        //return "redirect:/member/minecrowdfunding";
        //返回给浏览器，出来二维码页面
        return result;
    }



    private String payOrder(String ordernum, Integer moneny, String orderName, String remark) {
        try {
//获得初始化的AlipayClient
            AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.gatewayUrl, AlipayConfig.app_id, AlipayConfig.merchant_private_key, "json", AlipayConfig.charset, AlipayConfig.alipay_public_key, AlipayConfig.sign_type);

            //设置请求参数
            AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
            alipayRequest.setReturnUrl(AlipayConfig.return_url);
            alipayRequest.setNotifyUrl(AlipayConfig.notify_url);


            alipayRequest.setBizContent("{\"out_trade_no\":\""+ ordernum +"\","
                    + "\"total_amount\":\""+ moneny +"\","
                    + "\"subject\":\""+ orderName +"\","
                    + "\"body\":\""+ remark +"\","
                    + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");


            //请求
            String result = alipayClient.pageExecute(alipayRequest).getBody();

            return result;
        } catch (AlipayApiException e) {
            e.printStackTrace();
            return null;
        }

    }



    @ResponseBody
    @RequestMapping("order/updateOrderStatus")
    public String updateOrderStatus() {

        log.debug("支付宝支付通知，异步请求...");

        return "success";
    }

}
