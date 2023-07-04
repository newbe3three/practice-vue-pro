package com.practice.module.system.dal.dataobject.practiceapply;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import com.practice.framework.mybatis.core.dataobject.BaseDO;

/**
 * 实践申请 DO
 *
 * @author n3
 */
@TableName("system_practice_apply")
@KeySequence("system_practice_apply_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PracticeApplyDO extends BaseDO {

    /**
     * 实践申请编号
     */
    @TableId
    private Long id;
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 简历url
     */
    private String resume;
    /**
     * 附加信息
     */
    private String message;
    /**
     * 状态
     *
     * 枚举 {@link TODO practice_apply_status 对应的类}
     */
    private Byte status;
    /**
     * 申请实践id
     */
    private Long practiceId;

}
