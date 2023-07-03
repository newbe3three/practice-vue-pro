package com.practice.module.system.controller.admin.organizationother.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 其他组织更新 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class OrganizationOtherUpdateReqVO extends OrganizationOtherBaseVO {

    @Schema(description = "组织编号", required = true, example = "21953")
    @NotNull(message = "组织编号不能为空")
    private Long id;

}
