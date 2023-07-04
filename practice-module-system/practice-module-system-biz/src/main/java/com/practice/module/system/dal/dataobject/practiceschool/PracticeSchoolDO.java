package com.practice.module.system.dal.dataobject.practiceschool;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import com.practice.framework.mybatis.core.dataobject.BaseDO;

/**
 * 学校申请实践 DO
 *
 * @author n3
 */
@TableName("system_practice_school")
@KeySequence("system_practice_school_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PracticeSchoolDO extends BaseDO {

    /**
     * 编号
     */
    @TableId
    private Long id;
    /**
     * 申请学校id
     */
    private Long schoolId;
    /**
     * 申请实践id
     */
    private Long practiceId;
    /**
     * 状态
     *
     * 枚举 {@link TODO practice_school_status 对应的类}
     */
    private Byte status;

}
