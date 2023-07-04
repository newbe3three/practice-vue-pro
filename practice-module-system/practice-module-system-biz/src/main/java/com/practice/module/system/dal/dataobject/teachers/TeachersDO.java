package com.practice.module.system.dal.dataobject.teachers;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import com.practice.framework.mybatis.core.dataobject.BaseDO;

/**
 * 导师信息 DO
 *
 * @author n3
 */
@TableName("system_teachers")
@KeySequence("system_teachers_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TeachersDO extends BaseDO {

    /**
     * 导师id
     */
    @TableId
    private Long id;
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 姓名
     */
    private String name;
    /**
     * 部门ID
     */
    private Long deptId;
    /**
     * 所属专业
     */
    private String major;
    /**
     * 手机号码
     */
    private String mobile;
    /**
     * 身份证号
     */
    private String cardId;

}
