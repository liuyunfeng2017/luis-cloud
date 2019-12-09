package com.cloud.luis.user.service.impl;

import com.cloud.luis.user.mapper.PortalUserMapper;
import com.cloud.luis.user.model.PortalUser;
import com.cloud.luis.user.service.PortalUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author luis
 * @since 2019-12-06
 */
@Service
public class PortalUserServiceImpl extends ServiceImpl<PortalUserMapper, PortalUser> implements PortalUserService {

}
