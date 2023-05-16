package com.practice.module.system.dal.dataobject.task;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import com.practice.framework.mybatis.core.dataobject.BaseDO;

/**
 * 任务 DO
 *
 * @author n3
 */
@TableName("system_task")
@KeySequence("system_task_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskDO extends BaseDO {

    /**
     * 编号
     */
    @TableId
    private Long id;
    /**
     * 名字
     */
    private String name;
    /**
     * 部门名称
     */
    private String deptName;
    /**
     * 任务报酬 数据单位为分，前端单位为元
     */
    private Double amount;
    /**
     * 开始时间
     */
    private LocalDateTime startTime;
    /**
     * 结束时间
     */
    private LocalDateTime endTime;
    /**
     * 需要人数
     */
    private Integer numberPeople;
    /**
     * 状态
     *
     * 枚举 {@link TODO system_task_status 对应的类}
     */
    private Byte status;
    /**
     * 驳回意见
     */
    private String suggestion;

}
