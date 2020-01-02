package com.cloud.luis.user.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cloud.luis.user.model.PortalUser;

/**
 * 用户表 服务类
 * @author luis
 * @since 2019-12-06
 */
public interface PortalUserService extends IService<PortalUser> {
    
    public List<PortalUser> getUserByParams(PortalUser user);
    
    public Long getUserId();

}
