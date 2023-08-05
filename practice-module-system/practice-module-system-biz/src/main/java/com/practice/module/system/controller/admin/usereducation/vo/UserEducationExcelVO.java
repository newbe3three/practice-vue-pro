package com.practice.module.system.controller.admin.usereducation.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;

import com.alibaba.excel.annotation.ExcelProperty;

/**
 * 教育经历 Excel VO
 *
 * @author n3
 */
@Data
public class UserEducationExcelVO {

    @ExcelProperty("编号")
    private Long id;

    @ExcelProperty("用户编号")
    private Long userId;

    @ExcelProperty("专业")
    private String major;

    @ExcelProperty("指导老师")
    private String teacher;

    @ExcelProperty("学校名称")
    private String schoolName;

    @ExcelProperty("描述")
    private String description;

    @ExcelProperty("开始时间")
    private LocalDateTime startTime;

    @ExcelProperty("结束时间")
    private LocalDateTime endTime;

    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}
