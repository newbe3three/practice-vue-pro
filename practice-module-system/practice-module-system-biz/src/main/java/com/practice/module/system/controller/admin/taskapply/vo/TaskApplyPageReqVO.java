package com.practice.module.system.controller.admin.taskapply.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import com.practice.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static com.practice.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 任务申请分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class TaskApplyPageReqVO extends PageParam {

    @Schema(description = "用户编号", example = "1")
    private Long userId;

    @Schema(description = "用户所属企业编号", example = "1234")
    private Long companyId;

    @Schema(description = "创建者")
    private String creator;

    @Schema(description = "任务编号", example = "28559")
    private Long taskId;

    @Schema(description = "手机号码")
    private String mobile;

    @Schema(description = "状态", example = "2")
    private Byte status;

    @Schema(description = "申请/创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}
