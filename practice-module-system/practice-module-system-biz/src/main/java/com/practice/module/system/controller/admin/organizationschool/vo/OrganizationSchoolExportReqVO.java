package com.practice.module.system.controller.admin.organizationschool.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import com.practice.framework.common.pojo.PageParam;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;

import static com.practice.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 教育院校 Excel 导出 Request VO，参数和 OrganizationSchoolPageReqVO 是一致的")
@Data
public class OrganizationSchoolExportReqVO {

    @Schema(description = "院校编号", example = "27836")
    private Long id;

    @Schema(description = "院校名称", example = "张三")
    private String name;

    @Schema(description = "院校简称")
    private String abbreviation;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}
