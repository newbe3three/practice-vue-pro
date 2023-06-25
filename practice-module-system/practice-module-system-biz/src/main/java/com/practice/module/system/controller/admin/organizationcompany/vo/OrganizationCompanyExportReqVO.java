package com.practice.module.system.controller.admin.organizationcompany.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import com.practice.framework.common.pojo.PageParam;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;

import static com.practice.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 社会企业 Excel 导出 Request VO，参数和 OrganizationCompanyPageReqVO 是一致的")
@Data
public class OrganizationCompanyExportReqVO {

    @Schema(description = "企业编号", example = "25180")
    private Long id;

    @Schema(description = "企业名称", example = "芋艿")
    private String name;

    @Schema(description = "企业简称")
    private String abbreviation;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}
