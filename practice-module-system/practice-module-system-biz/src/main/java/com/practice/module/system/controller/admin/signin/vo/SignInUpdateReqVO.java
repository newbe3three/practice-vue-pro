package com.practice.module.system.controller.admin.signin.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 签到更新 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class SignInUpdateReqVO extends SignInBaseVO {

    @Schema(description = "编号", required = true, example = "20541")
    @NotNull(message = "编号不能为空")
    private Long id;

}
