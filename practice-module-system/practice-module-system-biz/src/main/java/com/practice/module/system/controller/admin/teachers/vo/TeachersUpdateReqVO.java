package com.practice.module.system.controller.admin.teachers.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 导师信息更新 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class TeachersUpdateReqVO extends TeachersBaseVO {

    @Schema(description = "导师id", required = true, example = "19242")
    @NotNull(message = "导师id不能为空")
    private Long id;

}
