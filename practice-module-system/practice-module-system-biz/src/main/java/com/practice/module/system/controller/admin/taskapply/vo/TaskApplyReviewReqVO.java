package com.practice.module.system.controller.admin.taskapply.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Schema(description = "管理后台 - 任务申请审核 Request VO")
@Data
@ToString(callSuper = true)
public class TaskApplyReviewReqVO {
    @Schema(description = "任务编号", required = true, example = "1")
    @NotNull(message = "任务编号不能为空")
    private Long id;

    @Schema(description = "审核结果", required = true, example = "true")
    @NotNull(message = "审核结果不能为空")
    private Boolean flag;
}
