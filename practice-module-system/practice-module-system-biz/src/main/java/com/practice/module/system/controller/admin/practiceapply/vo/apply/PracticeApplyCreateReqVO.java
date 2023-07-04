package com.practice.module.system.controller.admin.practiceapply.vo.apply;

import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.NotNull;

@Schema(description = "管理后台 - 实践申请创建 Request VO")
@Data

public class PracticeApplyCreateReqVO  {

   /* @Schema(description = "用户id", example = "31785")
    private Long userId;*/

    @Schema(description = "所申请实践id", required = true, example = "30503")
    @NotNull(message = "所申请实践id不能为空")
    private Long practiceId;

    @Schema(description = "简历url", required = true)
    @NotNull(message = "简历url不能为空")
    private String resume;

    @Schema(description = "附加信息", required = true)
    @NotNull(message = "附加信息不能为空")
    private String message;

    //发起申请时不需要传递状态，默认设置状态为0
    //@Schema(description = "状态", example = "2")
    //private Byte status;
}
