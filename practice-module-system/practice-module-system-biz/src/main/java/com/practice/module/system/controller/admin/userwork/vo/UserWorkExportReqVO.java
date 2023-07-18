package com.practice.module.system.controller.admin.userwork.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import com.practice.framework.common.pojo.PageParam;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;

import static com.practice.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 工作经历 Excel 导出 Request VO，参数和 UserWorkPageReqVO 是一致的")
@Data
public class UserWorkExportReqVO {

    @Schema(description = "用户编号", example = "23512")
    private Long userId;

    @Schema(description = "职位", example = "赵六")
    private String name;

    @Schema(description = "类型", example = "全职")
    private String type;

    @Schema(description = "公司名称", example = "王五")
    private String companyName;

    @Schema(description = "描述", example = "随便")
    private String description;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "开始时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] startTime;

    @Schema(description = "结束时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] endTime;

}
