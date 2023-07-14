package com.practice.module.system.controller.admin.task.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 任务 Response VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class TaskRespVO extends TaskBaseVO {

    @Schema(description = "编号", required = true, example = "26036")
    private Long id;

    @Schema(description = "企业名称", example = "111")
    private String companyName;

    @Schema(description = "创建时间", required = true)
    private LocalDateTime createTime;

}
