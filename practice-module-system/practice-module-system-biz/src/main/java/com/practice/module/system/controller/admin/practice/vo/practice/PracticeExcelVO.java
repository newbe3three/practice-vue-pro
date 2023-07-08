package com.practice.module.system.controller.admin.practice.vo.practice;

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

import javax.validation.constraints.NotNull;


/**
 * 实践 Excel VO
 *
 * @author n3
 */
@Data
public class PracticeExcelVO {

    @ExcelProperty("编号")
    private Long id;

    @ExcelProperty("名字")
    private String name;

    @ExcelProperty("实践内容")
    private String content;

    @ExcelProperty("职业要求")
    private String requirement;

    @ExcelProperty("开始时间")
    private LocalDateTime startTime;

    @ExcelProperty("结束时间")
    private LocalDateTime endTime;

    @ExcelProperty("需要人数")
    private Integer numberPeople;

    @ExcelProperty(value = "状态", converter = DictConvert.class)
    @DictFormat("practice_status") // TODO 代码优化：建议设置到对应的 XXXDictTypeConstants 枚举类中
    private Byte status;

    @ExcelProperty("驳回意见")
    private String suggestion;

    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}
