package com.practice.module.system.controller.admin.teachers.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;

import com.alibaba.excel.annotation.ExcelProperty;

/**
 * 导师信息 Excel VO
 *
 * @author n3
 */
@Data
public class TeachersExcelVO {

    @ExcelProperty("导师id")
    private Long id;

    @ExcelProperty("用户id")
    private Long userId;

    @ExcelProperty("姓名")
    private String name;

    @ExcelProperty("部门ID")
    private Long deptId;

    @ExcelProperty("所属专业")
    private String major;

    @ExcelProperty("手机号码")
    private String mobile;

    @ExcelProperty("身份证号")
    private String cardId;

    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}
