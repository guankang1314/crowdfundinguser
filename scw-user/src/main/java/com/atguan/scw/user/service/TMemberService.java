package com.atguan.scw.user.service;

import com.atguan.scw.user.bean.TMember;
import com.atguan.scw.user.bean.TMemberAddress;
import com.atguan.scw.user.vo.req.UserRegistVo;
import com.atguan.scw.user.vo.resp.UserRespVo;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface TMemberService {
    int saveTMember(UserRegistVo vo);

    UserRespVo getUserByLogin(String loginacct, String password);

    TMember getMebmerById(Integer id);

    List<TMemberAddress> listAddress(Integer memberId);
}
