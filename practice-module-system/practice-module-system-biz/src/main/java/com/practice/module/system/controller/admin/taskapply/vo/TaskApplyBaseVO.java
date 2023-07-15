package com.practice.module.system.controller.admin.taskapply.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import javax.validation.constraints.*;

/**
* 任务申请 Base VO，提供给添加、修改、详细的子 VO 使用
* 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
*/
@Data
public class TaskApplyBaseVO {

    @Schema(description = "用户编号", required = true, example = "1")
    @NotNull(message = "用户编号不能为空")
    private Long  userId;

    @Schema(description = "用户所属企业编号", required = true, example = "1")
    @NotNull(message = "用户所属企业编号不能为空")
    private Long companyId;

    @Schema(description = "任务id", required = true, example = "28559")
    @NotNull(message = "任务id不能为空")
    private Long taskId;

    @Schema(description = "手机号码")
    private String mobile;

    @Schema(description = "状态", example = "2")
    private Byte status;

}
