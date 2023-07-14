package com.practice.module.system.controller.admin.task.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;

import com.alibaba.excel.annotation.ExcelProperty;
import com.practice.framework.excel.core.annotations.DictFormat;
import com.practice.framework.excel.core.convert.DictConvert;


/**
 * 任务 Excel VO
 *
 * @author n3
 */
@Data
public class TaskExcelVO {

    @ExcelProperty("编号")
    private Long id;

    @ExcelProperty("名字")
    private String name;

    @ExcelProperty("企业编号")
    private Long companyId;

    @ExcelProperty("企业名称")
    private String  companyName;

    @ExcelProperty("任务报酬")
    private Double amount;

    @ExcelProperty("开始时间")
    private LocalDateTime startTime;

    @ExcelProperty("结束时间")
    private LocalDateTime endTime;

    @ExcelProperty("需要人数")
    private Integer numberPeople;

    @ExcelProperty(value = "状态", converter = DictConvert.class)
    @DictFormat("system_task_status") // TODO 代码优化：建议设置到对应的 XXXDictTypeConstants 枚举类中
    private Byte status;

    @ExcelProperty("驳回意见")
    private String suggestion;

    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}
