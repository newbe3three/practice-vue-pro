package com.practice.module.system.controller.admin.practiceschool.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import com.practice.framework.common.pojo.PageParam;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;

import static com.practice.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 学校申请实践 Excel 导出 Request VO，参数和 PracticeSchoolPageReqVO 是一致的")
@Data
public class PracticeSchoolExportReqVO {

    @Schema(description = "申请学校id", example = "2828")
    private Long schoolId;

    @Schema(description = "申请实践id", example = "11135")
    private Long practiceId;

    @Schema(description = "状态", example = "2")
    private Byte status;

    @Schema(description = "申请/创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}
