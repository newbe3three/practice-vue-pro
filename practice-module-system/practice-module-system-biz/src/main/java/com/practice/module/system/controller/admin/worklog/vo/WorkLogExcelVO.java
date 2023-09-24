package com.practice.module.system.controller.admin.worklog.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;

import com.alibaba.excel.annotation.ExcelProperty;

/**
 * 工作日志 Excel VO
 *
 * @author 觅践
 */
@Data
public class WorkLogExcelVO {

    @ExcelProperty("编号")
    private Long id;

    @ExcelProperty("用户id")
    private Long userId;

    @ExcelProperty("实践id")
    private Long practiceId;

    @ExcelProperty("今日已完成工作")
    private String finishTask;

    @ExcelProperty("待完成工作")
    private String todoTask;

    @ExcelProperty("待协调工作")
    private String toDiscussTask;

    @ExcelProperty("申请/创建时间")
    private LocalDateTime createTime;

}
