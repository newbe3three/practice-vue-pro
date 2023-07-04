package com.practice.module.system.dal.dataobject.students;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import com.practice.framework.mybatis.core.dataobject.BaseDO;

/**
 * 学生信息 DO
 *
 * @author n3
 */
@TableName("system_students")
@KeySequence("system_students_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudentsDO extends BaseDO {

    /**
     * 学生id
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
     * 指导教师id
     */
    private Long teacherId;
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
