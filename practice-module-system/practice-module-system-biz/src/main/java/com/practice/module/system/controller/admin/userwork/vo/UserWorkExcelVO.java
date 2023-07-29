package com.practice.module.system.controller.admin.userwork.vo;

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
 * 工作经历 Excel VO
 *
 * @author n3
 */
@Data
public class UserWorkExcelVO {

    @ExcelProperty("编号")
    private Long id;

    @ExcelProperty("用户编号")
    private Long userId;

    @ExcelProperty("职位")
    private String name;

    @ExcelProperty(value = "类型", converter = DictConvert.class)
    @DictFormat("work_type") // TODO 代码优化：建议设置到对应的 XXXDictTypeConstants 枚举类中
    private String type;

    @ExcelProperty("公司名称")
    private String companyName;

    @ExcelProperty("描述")
    private String description;

    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @ExcelProperty("开始时间")
    private LocalDateTime startTime;

    @ExcelProperty("结束时间")
    private LocalDateTime endTime;

}
