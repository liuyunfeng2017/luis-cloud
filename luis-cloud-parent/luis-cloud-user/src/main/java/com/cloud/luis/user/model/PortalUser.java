package com.cloud.luis.user.model;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author luis
 * @since 2019-12-06
 */
@Data
@TableName("t_portal_user")
public class PortalUser {
	
	@TableId
	private Long id;

    /**
     * 用户名，1、不建议填中文。2、建议填手机号
     */
    private String userName;

    /**
     * 手机号，不唯一，业务系统存在一个手机号对应多个账号的情况
     */
    private String userMobile;

    /**
     * 真实姓名
     */
    private String trueName;

    /**
     * 妮称，可填姓名
     */
    private String nickName;

    /**
     * 密码，导入用户数据时，密码为空
     */
    private String userPwd;

    /**
     * 性别 0女 1男 2未知
     */
    private Integer userSex;

    /**
     * 状态 -1己删除 0禁用 1正常
     */
    private Integer userStatus;

    /**
     * 头像
     */
    private String headImage;

    /**
     * 邮箱
     */
    private String userEmail;

    /**
     * 微信ID
     */
    private String weixinUnionId;

    /**
     * 钉钉ID
     */
    private String dingtalkOpenid;

    /**
     * 1首次登陆(未设置密码)、0非首次登陆
     */
    private Boolean firstLoginFlag;

    /**
     * 用户名修改次数
     */
    private Integer changeUserNameTimes;

    /**
     * QQID
     */
    private String qqOpenid;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建人
     */
    private Long createrBy;

    /**
     * 创建时间
     */
    private LocalDateTime createrTime;

    /**
     * 修改人
     */
    private Long updateBy;

    /**
     * 修改时间
     */
    private LocalDateTime updateTime;


}
