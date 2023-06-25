package com.practice.module.system.dal.dataobject.organizationcompany;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import com.practice.framework.mybatis.core.dataobject.BaseDO;

/**
 * 社会企业 DO
 *
 * @author wx
 */
@TableName("system_organization_company")
@KeySequence("system_organization_company_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrganizationCompanyDO extends BaseDO {

    /**
     * 企业编号
     */
    @TableId
    private Long id;
    /**
     * 企业名称
     */
    private String name;
    /**
     * 企业简称
     */
    private String abbreviation;
    /**
     * 入口地址
     */
    private String ref;
    /**
     * 联系邮箱
     */
    private String email;
    /**
     * 联系电话
     */
    private String phone;
    /**
     * 负责人
     */
    private String principal;
    /**
     * 负责人性别
     */
    private String principalSex;
    /**
     * 联系微信
     */
    private String principalWechat;
    /**
     * 负责人职务
     */
    private String principalPosition;
    /**
     * 统一社会信用代码
     */
    private String code;
    /**
     * 地址
     */
    private String address;
    /**
     * 注册地址
     */
    private String registeredAddress;
    /**
     * 法人
     */
    private String legalPerson;

}
