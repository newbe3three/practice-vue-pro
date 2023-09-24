package com.practice.module.system.controller.admin.worklog.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import javax.validation.constraints.*;

/**
* 工作日志 Base VO，提供给添加、修改、详细的子 VO 使用
* 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
*/
@Data
public class WorkLogBaseVO {

    @Schema(description = "用户id", required = true, example = "21754")
    @NotNull(message = "用户id不能为空")
    private Long userId;

    @Schema(description = "实践id", required = true, example = "29557")
    @NotNull(message = "实践id不能为空")
    private Long practiceId;

    @Schema(description = "今日已完成工作", required = true)
    @NotNull(message = "今日已完成工作不能为空")
    private String finishTask;

    @Schema(description = "待完成工作", required = true)
    @NotNull(message = "待完成工作不能为空")
    private String todoTask;

    @Schema(description = "待协调工作", required = true)
    @NotNull(message = "待协调工作不能为空")
    private String toDiscussTask;

}
