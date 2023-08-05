package com.practice.module.system.controller.admin.userskill.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 个人技能 Response VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class UserSkillRespVO extends UserSkillBaseVO {

    @Schema(description = "编号", required = true, example = "18839")
    private Long id;

   // @Schema(description = "创建时间", required = true)
    //private LocalDateTime createTime;

}
