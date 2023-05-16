package com.practice.module.system.controller.admin.task.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 任务更新 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class TaskUpdateReqVO extends TaskBaseVO {

    @Schema(description = "编号", required = true, example = "26036")
    @NotNull(message = "编号不能为空")
    private Long id;

}
