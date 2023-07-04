package com.practice.module.system.controller.admin.practiceapply.vo.apply;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.*;

@Schema(description = "管理后台 - 实践申请更新 Request VO")
@Data

public class PracticeApplyUpdateReqVO {

    @Schema(description = "实践申请编号", required = true, example = "28491")
    @NotNull(message = "实践申请编号不能为空")
    private Long id;

//    @Schema(description = "用户id", example = "31785")
//    private Long userId;

    @Schema(description = "申请实践id", required = true, example = "30503")
    @NotNull(message = "申请实践id不能为空")
    private Long practiceId;

    @Schema(description = "简历url", required = true)
    @NotNull(message = "简历url不能为空")
    private String resume;

    @Schema(description = "附加信息", required = true)
    @NotNull(message = "附加信息不能为空")
    private String message;

}
