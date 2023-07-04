package com.practice.module.system.controller.admin.practiceschool.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 学校申请实践更新 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class PracticeSchoolUpdateReqVO extends PracticeSchoolBaseVO {

    @Schema(description = "编号", required = true, example = "26607")
    @NotNull(message = "编号不能为空")
    private Long id;

    @Schema(description = "状态", example = "2")
    private Byte status;
}
