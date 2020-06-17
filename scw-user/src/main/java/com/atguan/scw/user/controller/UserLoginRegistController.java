package com.atguan.scw.user.controller;

import com.atguan.scw.enums.AccttypeEnume;
import com.atguan.scw.enums.AuthEnume;
import com.atguan.scw.enums.UserTypeEnume;
import com.atguan.scw.user.bean.TMember;
import com.atguan.scw.user.component.SmsTemplate;
import com.atguan.scw.user.service.TMemberService;
import com.atguan.scw.user.vo.req.UserRegistVo;
import com.atguan.scw.user.vo.resp.UserRespVo;
import com.atguan.scw.vo.resp.AppResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Slf4j
@Api(tags = "用户登陆注册模块")
@RequestMapping("/user")
@RestController
public class UserLoginRegistController {
	
	@Autowired
	SmsTemplate smsTemplate;
	
	@Autowired
	StringRedisTemplate stringRedisTemplate;

	@Autowired
	TMemberService memberService;
	
	
	@ApiOperation(value="用户登陆")
	@ApiImplicitParams(value={
			@ApiImplicitParam(value="登陆账号",name="loginacct"),
			@ApiImplicitParam(value="用户密码",name="password")
	})
	@PostMapping("/login")
	public AppResponse<UserRespVo> login(@RequestParam("loginacct") String loginacct, @RequestParam("password") String password){


		log.debug("登录-{}",loginacct);
		log.debug("密码-{}",password);



		try {

			UserRespVo vo = memberService.getUserByLogin(loginacct,password);

			log.debug("登录成功-{}",loginacct);

			return AppResponse.ok(vo);
		} catch (Exception e) {
			e.printStackTrace();

			log.debug("登录失败-{}-{}",loginacct,e.getMessage());


			AppResponse resp = AppResponse.fail(null);
			resp.setMsg(e.getMessage());
			return resp;
		}
	}
	
	@ApiOperation(value="用户注册") 
	@PostMapping("/register")
	public AppResponse<Object> register(UserRegistVo vo){


		String loginacct = vo.getLoginacct();

		if (!StringUtils.isEmpty(loginacct)) {

			String code = stringRedisTemplate.opsForValue().get(loginacct);

			if (!StringUtils.isEmpty(code)) {

				if (code.equals(vo.getCode())){

					int i = memberService.saveTMember(vo);

					if (i==1){
						//清理缓存
						stringRedisTemplate.delete(loginacct);
						return AppResponse.ok("ok");

					}else {
						return AppResponse.fail(null);
					}

				}else {
					AppResponse resp = AppResponse.fail(null);
					resp.setMsg("验证码不一致");
					return resp;
				}


			}else {
				AppResponse resp = AppResponse.fail(null);
				resp.setMsg("验证码已失效");
				return resp;
			}

		}else {

			AppResponse resp = AppResponse.fail(null);
			resp.setMsg("用户名称不能为空");
			return resp;
		}


	} 
	
	@ApiOperation(value="发送短信验证码") 
	@PostMapping("/sendsms")
	public AppResponse<Object> sendsms(String loginacct){
		
		StringBuilder code = new StringBuilder();
		for (int i = 1; i <= 4; i++) {
			code.append(new Random().nextInt(10));
		}
		
		Map<String, String> querys = new HashMap<String, String>();
	    querys.put("mobile", loginacct);
	    querys.put("param", "code:"+code.toString());
	    querys.put("tpl_id", "TP1711063");
	    
		smsTemplate.sendSms(querys);
		
		stringRedisTemplate.opsForValue().set(loginacct, code.toString(),5, TimeUnit.MINUTES);
		
		log.debug("发送短信成功-验证码：{}",code.toString());
		
		return AppResponse.ok("ok");
	} 
	
	@ApiOperation(value="验证短信验证码") 
	@PostMapping("/valide")
	public AppResponse<Object> valide(){
		return AppResponse.ok("ok");
	} 
	
	@ApiOperation(value="重置密码") 
	@PostMapping("/reset")
	public AppResponse<Object> reset(){
		return AppResponse.ok("ok");
	} 
	
	

}
