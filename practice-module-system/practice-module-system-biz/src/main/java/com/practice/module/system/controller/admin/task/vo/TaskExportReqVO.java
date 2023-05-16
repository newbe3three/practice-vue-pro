package com.practice.module.system.controller.admin.task.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import com.practice.framework.common.pojo.PageParam;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;

import static com.practice.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 任务 Excel 导出 Request VO，参数和 TaskPageReqVO 是一致的")
@Data
public class TaskExportReqVO {

    @Schema(description = "名字", example = "李四")
    private String name;

    @Schema(description = "部门名称", example = "张三")
    private String deptName;

    @Schema(description = "任务报酬")
    private Double amount;

    @Schema(description = "开始时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] startTime;

    @Schema(description = "结束时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] endTime;

    @Schema(description = "需要人数")
    private Integer numberPeople;

    @Schema(description = "状态", example = "2")
    private Byte status;

    @Schema(description = "驳回意见")
    private String suggestion;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}
