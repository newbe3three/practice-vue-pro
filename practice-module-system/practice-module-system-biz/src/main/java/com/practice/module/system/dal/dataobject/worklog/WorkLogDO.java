package com.practice.module.system.dal.dataobject.worklog;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import com.practice.framework.mybatis.core.dataobject.BaseDO;

/**
 * 工作日志 DO
 *
 * @author 觅践
 */
@TableName("system_work_log")
@KeySequence("system_work_log_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WorkLogDO extends BaseDO {

    /**
     * 编号
     */
    @TableId
    private Long id;
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 实践id
     */
    private Long practiceId;
    /**
     * 今日已完成工作
     */
    private String finishTask;
    /**
     * 待完成工作
     */
    private String todoTask;
    /**
     * 待协调工作
     */
    private String toDiscussTask;

}
