package com.practice.module.system.controller.admin.userskill.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import javax.validation.constraints.*;

/**
* 个人技能 Base VO，提供给添加、修改、详细的子 VO 使用
* 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
*/
@Data
public class UserSkillBaseVO {

    @Schema(description = "用户编号", required = true, example = "17603")
   // @NotNull(message = "用户编号不能为空")
    private Long userId;

    @Schema(description = "技能", required = true)
    @NotNull(message = "技能不能为空")
    private String skill;

    @Schema(description = "技能等级", required = true)
    @NotNull(message = "技能等级不能为空")
    private String level;

}
