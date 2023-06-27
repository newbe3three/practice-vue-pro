package com.practice.module.system.dal.dataobject.organizationschool;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import com.practice.framework.mybatis.core.dataobject.BaseDO;

/**
 * 教育院校 DO
 *
 * @author wx
 */
@TableName("system_organization_school")
@KeySequence("system_organization_school_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrganizationSchoolDO extends BaseDO {

    /**
     * 院校编号
     */
    @TableId
    private Long id;
    /**
     * 院校名称
     */
    private String name;
    /**
     * 院校简称
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
     * 开始时间
     */
    private String startTime;
    /**
     * 终止时间
     */
    private String endTime;
    /**
     * 是否终止服务
     */
    private Boolean isEnd;
}
