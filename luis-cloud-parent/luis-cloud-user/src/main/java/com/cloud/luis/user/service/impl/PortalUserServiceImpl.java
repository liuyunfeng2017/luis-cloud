package com.cloud.luis.user.service.impl;

import com.cloud.luis.user.mapper.PortalUserMapper;
import com.cloud.luis.user.model.PortalUser;
import com.cloud.luis.user.service.PortalUserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 用户表 服务实现类
 * 
 * @author luis
 * @since 2019-12-06
 */
@Service
public class PortalUserServiceImpl extends ServiceImpl<PortalUserMapper, PortalUser> implements PortalUserService {

    @Autowired
    PortalUserMapper portalUserMapper;

    /**
     * 通过手机号查询用户
     */
    @Override
    public List<PortalUser> getUserByParams(PortalUser user) {
        QueryWrapper<PortalUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_mobile", user.getUserMobile());
        return portalUserMapper.selectList(queryWrapper);
    }

}
