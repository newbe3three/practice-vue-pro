package com.practice.module.system.controller.admin.signin.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 签到 Response VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class SignInRespVO extends SignInBaseVO {

    @Schema(description = "编号", required = true, example = "20541")
    private Long id;

    @Schema(description = "申请/创建时间", required = true)
    private LocalDateTime createTime;

}
