package com.practice.module.system.dal.dataobject.userskill;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import com.practice.framework.mybatis.core.dataobject.BaseDO;

/**
 * 个人技能 DO
 *
 * @author n3
 */
@TableName("system_user_skill")
@KeySequence("system_user_skill_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserSkillDO extends BaseDO {

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
     * 技能
     */
    private String skill;
    /**
     * 技能等级
     *
     * 枚举 {@link TODO user_skill_level 对应的类}
     */
    private String level;

}
