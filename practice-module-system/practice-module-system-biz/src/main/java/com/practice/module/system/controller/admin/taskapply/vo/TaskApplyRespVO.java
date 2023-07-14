package com.practice.module.system.controller.admin.taskapply.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 任务申请 Response VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class TaskApplyRespVO extends TaskApplyBaseVO {

    @Schema(description = "编号", required = true, example = "1964")
    private Long id;

    @Schema(description = "申请/创建时间", required = true)
    private LocalDateTime createTime;

    @Schema(description = "用户所属企业名", required = true, example = "李四")
    @NotNull(message = "用户所属企业名不能为空")
    private String companyName;

    @Schema(description = "任务名称", required = true, example = "李四")
    @NotNull(message = "部门名称不能为空")
    private String taskName;

    @Schema(description = "用户名称", required = true, example = "张三")
    @NotNull(message = "用户名称不能为空")
    private String userName;
}
