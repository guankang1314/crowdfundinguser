package com.atguan.scw.project.service.impl;


import com.alibaba.fastjson.JSON;
import com.atguan.scw.project.bean.*;
import com.atguan.scw.project.constant.ProjectConstant;
import com.atguan.scw.project.mapper.*;
import com.atguan.scw.project.service.TProjectService;
import com.atguan.scw.project.vo.req.ProjectRedisStorageVo;
import com.atguan.scw.util.AppDateUtils;
import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Slf4j
@Service
public class TProjectServiceImpl implements TProjectService {


    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Autowired
    TProjectMapper projectMapper;

    @Autowired
    TProjectImagesMapper projectImagesMapper;

    @Autowired
    TReturnMapper returnMapper;

    @Autowired
    TProjectTypeMapper projectTypeMapper;

    @Autowired
    TProjectTagMapper projectTagMapper;

    @Transactional
    @Override
    public void saveProject(String accessToken, String projectToken, byte code) {


        String memberId = stringRedisTemplate.opsForValue().get(accessToken);


        //从redis中获取bigVo数据
        String bigStr = stringRedisTemplate.opsForValue().get(ProjectConstant.TEMP_PROJECT_PREFIX+projectToken);
        ProjectRedisStorageVo bigVo = JSON.parseObject(bigStr,ProjectRedisStorageVo.class);

        //保存项目
        TProject project = new TProject();
        project.setName(bigVo.getName());
        project.setRemark(bigVo.getRemark());
        project.setDay(bigVo.getDay());
        project.setMoney((long)bigVo.getMoney());
        project.setStatus(code+"");
        project.setMemberid(Integer.parseInt(memberId));
        project.setCreatedate(AppDateUtils.getFormatTime());

        projectMapper.insertSelective(project);

        //主键回填
        Integer projectId = project.getId();
        log.debug("保存项目id={}",projectId);


        //保存图片
        TProjectImages projectImages = new TProjectImages();
        projectImages.setProjectid(projectId);
        projectImages.setImgurl(bigVo.getHeaderImage());
        projectImages.setImgtype((byte)0);
        projectImagesMapper.insertSelective(projectImages);

        List<String> detailsImage = bigVo.getDetailsImage();

        for (String imgPath : detailsImage) {
            TProjectImages pi = new TProjectImages();
            pi.setProjectid(projectId);
            pi.setImgurl(imgPath);
            pi.setImgtype((byte)1);
            projectImagesMapper.insertSelective(pi);
        }


        //保存回报
        List<TReturn> projectReturns = bigVo.getProjectReturns();
        for (TReturn retObj : projectReturns) {
            retObj.setProjectid(projectId);
            returnMapper.insertSelective(retObj);
        }

        //保存项目与分类关系

        List<Integer> typeids = bigVo.getTypeids();

        for (Integer typeId : typeids) {
            TProjectType pt = new TProjectType();
            pt.setProjectid(projectId);
            pt.setTypeid(typeId);
            projectTypeMapper.insertSelective(pt);
        }

        //保存项目与标签关系
        List<Integer> tagids = bigVo.getTagids();
        for (Integer tagId : tagids) {
            TProjectTag pt = new TProjectTag();
            pt.setProjectid(projectId);
            pt.setTagid(tagId);
            projectTagMapper.insertSelective(pt);
        }

        //清理缓存
        stringRedisTemplate.delete(ProjectConstant.TEMP_PROJECT_PREFIX+projectToken);

    }
}
