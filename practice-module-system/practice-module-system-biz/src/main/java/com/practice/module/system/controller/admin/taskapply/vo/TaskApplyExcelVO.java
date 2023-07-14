package com.practice.module.system.controller.admin.taskapply.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;

import com.alibaba.excel.annotation.ExcelProperty;
import com.practice.framework.excel.core.annotations.DictFormat;
import com.practice.framework.excel.core.convert.DictConvert;


/**
 * 任务申请 Excel VO
 *
 * @author n3
 */
@Data
public class TaskApplyExcelVO {

    @ExcelProperty("编号")
    private Long id;

    @ExcelProperty("用户编号")
    private Long userId;

    @ExcelProperty("用户名称")
    private String userName;

    @ExcelProperty("用户所属部门编号")
    private Long companyId;

    @ExcelProperty("用户所属企业名")
    private String companyName;

    @ExcelProperty("用户任务编号")
    private Long taskId;
    @ExcelProperty("用户任务名称")
    private String taskName;

    @ExcelProperty("手机号码")
    private String mobile;

    @ExcelProperty(value = "状态", converter = DictConvert.class)
    @DictFormat("system_task_apply_status") // TODO 代码优化：建议设置到对应的 XXXDictTypeConstants 枚举类中
    private Byte status;

    @ExcelProperty("申请/创建时间")
    private LocalDateTime createTime;

}
