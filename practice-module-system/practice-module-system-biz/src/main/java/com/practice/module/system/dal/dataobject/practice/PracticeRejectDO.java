package com.practice.module.system.dal.dataobject.practice;

import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.practice.framework.mybatis.core.dataobject.BaseDO;
import lombok.*;

@TableName("system_practice_reject")
@KeySequence("system_practice_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PracticeRejectDO extends BaseDO {
    /**
     * 编号
     */
    @TableId
    private Long id;
    /**
     *  实践编号
     */
    private Long practiceId;

    /**
     * 驳回意见
     */
    private String suggestion;
}
