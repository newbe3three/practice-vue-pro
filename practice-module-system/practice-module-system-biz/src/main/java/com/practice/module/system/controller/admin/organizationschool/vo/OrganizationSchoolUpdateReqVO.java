package com.practice.module.system.controller.admin.organizationschool.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 教育院校更新 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class OrganizationSchoolUpdateReqVO extends OrganizationSchoolBaseVO {

    @Schema(description = "院校编号", required = true, example = "27836")
    @NotNull(message = "院校编号不能为空")
    private Long id;

}
