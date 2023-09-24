package com.practice.module.system.controller.admin.signin.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Schema(description = "管理后台 - 签到更新 Request VO")
@Data
public class SignInQueryByIdVO {
    @Schema(description = "用户id", example = "12026")
    private Long userId;

    @Schema(description = "实践id", required = true, example = "15288")
    @NotNull(message = "实践id不能为空")
    private Long practiceId;

}
