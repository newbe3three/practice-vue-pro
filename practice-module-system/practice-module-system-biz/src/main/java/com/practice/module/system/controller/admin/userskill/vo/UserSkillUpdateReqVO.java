package com.practice.module.system.controller.admin.userskill.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 个人技能更新 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class UserSkillUpdateReqVO extends UserSkillBaseVO {

    @Schema(description = "编号", required = true, example = "18839")
    @NotNull(message = "编号不能为空")
    private Long id;

}
