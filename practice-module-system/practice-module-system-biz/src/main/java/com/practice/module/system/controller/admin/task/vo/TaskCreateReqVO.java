package com.practice.module.system.controller.admin.task.vo;

import lombok.*;

import java.time.LocalDateTime;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;

import static com.practice.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 任务创建 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class TaskCreateReqVO extends TaskBaseVO {
    @Schema(description = "名字", required = true, example = "李四")
    @NotNull(message = "名字不能为空")
    private String name;

    @Schema(description = "部门名称", required = true, example = "张三")
    @NotNull(message = "部门名称不能为空")
    private String deptName;

    @Schema(description = "任务报酬", required = true)
    @NotNull(message = "任务报酬不能为空")
    private Double amount;

    @Schema(description = "开始时间", required = true)
    @NotNull(message = "开始时间不能为空")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime startTime;

    @Schema(description = "结束时间", required = true)
    @NotNull(message = "结束时间不能为空")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime endTime;

    @Schema(description = "需要人数", required = true)
    @NotNull(message = "需要人数不能为空")
    private Integer numberPeople;

    @Schema(description = "状态", required = true, example = "2")
    //数据库默认设置初始状态为0，不需要前端传递
    private Byte status;

    @Schema(description = "驳回意见")
    private String suggestion;
}
