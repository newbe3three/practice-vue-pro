package com.practice.module.system.dal.dataobject.taskapply;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import com.practice.framework.mybatis.core.dataobject.BaseDO;

/**
 * 任务申请 DO
 *
 * @author n3
 */
@TableName("system_task_apply")
@KeySequence("system_task_apply_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskApplyDO extends BaseDO {

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
     * 用户所属企业编号
     */
    private Long companyId;
    /**
     * 任务id
     */
    private Long taskId;
    /**
     * 手机号码
     */
    private String mobile;
    /**
     * 状态
     *
     *
     */
    private Byte status;

}
