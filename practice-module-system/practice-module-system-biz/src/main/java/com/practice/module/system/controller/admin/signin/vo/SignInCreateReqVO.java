package com.practice.module.system.controller.admin.signin.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 签到创建 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class SignInCreateReqVO extends SignInBaseVO {
    @Schema(description = "编号", required = true, example = "20541")
//    @NotNull(message = "编号不能为空")
    private Long id;

}
