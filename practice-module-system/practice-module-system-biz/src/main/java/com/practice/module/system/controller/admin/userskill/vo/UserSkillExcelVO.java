package com.practice.module.system.controller.admin.userskill.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;

import com.alibaba.excel.annotation.ExcelProperty;
import com.practice.framework.excel.core.annotations.DictFormat;
import com.practice.framework.excel.core.convert.DictConvert;


/**
 * 个人技能 Excel VO
 *
 * @author n3
 */
@Data
public class UserSkillExcelVO {

    @ExcelProperty("编号")
    private Long id;

    @ExcelProperty("用户编号")
    private Long userId;

    @ExcelProperty("技能")
    private String skill;

    @ExcelProperty(value = "技能等级", converter = DictConvert.class)
    @DictFormat("user_skill_level") // TODO 代码优化：建议设置到对应的 XXXDictTypeConstants 枚举类中
    private String level;

    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}
