package com.practice.module.system.controller.admin.userwork.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 工作经历更新 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class UserWorkUpdateReqVO extends UserWorkBaseVO {

    @Schema(description = "编号", required = true, example = "16804")
    @NotNull(message = "编号不能为空")
    private Long id;

}
