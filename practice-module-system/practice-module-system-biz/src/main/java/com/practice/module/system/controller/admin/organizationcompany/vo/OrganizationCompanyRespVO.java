package com.practice.module.system.controller.admin.organizationcompany.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 社会企业 Response VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class OrganizationCompanyRespVO extends OrganizationCompanyBaseVO {

    @Schema(description = "企业编号", required = true, example = "25180")
    private Long id;

    @Schema(description = "创建时间", required = true)
    private LocalDateTime createTime;

    @Schema(description = "开始时间")
    private String startTime;

    @Schema(description = "终止时间")
    private String endTime;

}
