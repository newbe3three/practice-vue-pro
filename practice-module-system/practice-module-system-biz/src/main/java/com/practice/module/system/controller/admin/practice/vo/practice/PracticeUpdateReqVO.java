package com.practice.module.system.controller.admin.practice.vo.practice;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.*;

@Schema(description = "管理后台 - 实践更新 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class PracticeUpdateReqVO extends PracticeBaseVO {

    @Schema(description = "编号", required = true, example = "5549")
    @NotNull(message = "编号不能为空")
    private Long id;


}
