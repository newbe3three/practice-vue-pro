package com.practice.module.system.controller.admin.task.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.NotNull;
@Schema(description = "管理后台 - 任务审核 Request VO")
//@Data set、get方法
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class TaskReviewReqVO extends TaskBaseVO{
    @Schema(description = "编号", required = true, example = "26036")
    @NotNull(message = "编号不能为空")
    private Long id;

//    @Schema(description = "驳回意见", required = true, example = "不合格")
//    @NotNull(message = "驳回意见不能为空")
//    private String suggestion;
//
//    @Schema(description = "审核结果", required = true, example = "true")
//    @NotNull(message = "审核结果不能为空")
//    private Boolean flag;
}
