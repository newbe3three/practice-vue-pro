package com.practice.module.system.controller.admin.practiceschool.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;

import com.alibaba.excel.annotation.ExcelProperty;
import com.practice.framework.excel.core.annotations.DictFormat;
import com.practice.framework.excel.core.convert.DictConvert;


/**
 * 学校申请实践 Excel VO
 *
 * @author n3
 */
@Data
public class PracticeSchoolExcelVO {

    @ExcelProperty("编号")
    private Long id;

    @ExcelProperty("申请学校id")
    private Long schoolId;

    @ExcelProperty("申请实践id")
    private Long practiceId;

    @ExcelProperty(value = "状态", converter = DictConvert.class)
    @DictFormat("practice_school_status") // TODO 代码优化：建议设置到对应的 XXXDictTypeConstants 枚举类中
    private Byte status;

    @ExcelProperty("申请/创建时间")
    private LocalDateTime createTime;

}
