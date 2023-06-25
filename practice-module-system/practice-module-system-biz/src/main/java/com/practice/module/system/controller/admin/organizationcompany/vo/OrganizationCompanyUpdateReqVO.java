package com.practice.module.system.controller.admin.organizationcompany.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 社会企业更新 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class OrganizationCompanyUpdateReqVO extends OrganizationCompanyBaseVO {

    @Schema(description = "企业编号", required = true, example = "25180")
    @NotNull(message = "企业编号不能为空")
    private Long id;

}
