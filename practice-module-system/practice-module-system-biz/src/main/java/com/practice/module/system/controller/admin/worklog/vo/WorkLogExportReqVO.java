package com.practice.module.system.controller.admin.worklog.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import com.practice.framework.common.pojo.PageParam;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;

import static com.practice.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 工作日志 Excel 导出 Request VO，参数和 WorkLogPageReqVO 是一致的")
@Data
public class WorkLogExportReqVO {

    @Schema(description = "用户id", example = "21754")
    private Long userId;

    @Schema(description = "实践id", example = "29557")
    private Long practiceId;

    @Schema(description = "申请/创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}
