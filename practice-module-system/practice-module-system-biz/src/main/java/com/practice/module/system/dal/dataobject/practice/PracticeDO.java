package com.practice.module.system.dal.dataobject.practice;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import com.practice.framework.mybatis.core.dataobject.BaseDO;

/**
 * 实践 DO
 *
 * @author n3
 */
@TableName("system_practice")
@KeySequence("system_practice_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PracticeDO extends BaseDO {

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
     * 实践内容
     */
    private String content;
    /**
     *任职
     * */
    private String requirement;
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
     *
     */
    private Byte status;

    /**
     * 所属企业id
     *
     * */
    private Long companyId;

}
