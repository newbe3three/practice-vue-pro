package com.practice.module.system.controller.admin.usereducation.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 教育经历更新 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class UserEducationUpdateReqVO extends UserEducationBaseVO {

    @Schema(description = "编号", required = true, example = "18370")
    @NotNull(message = "编号不能为空")
    private Long id;

}
