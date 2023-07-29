package com.practice.module.system.dal.dataobject.userwork;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import com.practice.framework.mybatis.core.dataobject.BaseDO;

/**
 * 工作经历 DO
 *
 * @author n3
 */
@TableName("system_user_work")
@KeySequence("system_user_work_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserWorkDO extends BaseDO {

    /**
     * 编号
     */
    @TableId
    private Long id;
    /**
     * 用户编号
     */
    private Long userId;
    /**
     * 职位
     */
    private String name;
    /**
     * 类型
     *
     * 枚举 {@link TODO work_type 对应的类}
     */
    private String type;
    /**
     * 公司名称
     */
    private String companyName;
    /**
     * 描述
     */
    private String description;
    /**
     * 开始时间
     */
    private LocalDateTime startTime;
    /**
     * 结束时间
     */
    private LocalDateTime endTime;

}
