package com.practice.module.system.controller.admin.practice.vo.practice;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import javax.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;

import static com.practice.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

/**
* 实践 Base VO，提供给添加、修改、详细的子 VO 使用
* 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
*/
@Data
public class PracticeBaseVO {

    @Schema(description = "名称", required = true, example = "工程师")
    @NotNull(message = "名称不能为空")
    private String name;

    @Schema(description = "实践内容", required = true)
    @NotNull(message = "实践内容不能为空")
    private String content;

    @Schema(description = "职业要求")
    @NotNull(message = "职业要求不能为空")
    private String requirement;

    @Schema(description = "企业id")
    private Long companyId;

    @Schema(description = "开始时间", required = true)
    @NotNull(message = "开始时间不能为空")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime startTime;

    @Schema(description = "结束时间", required = true)
    @NotNull(message = "结束时间不能为空")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime endTime;

    @Schema(description = "需要人数", required = true)
    @NotNull(message = "需要人数不能为空")
    private Integer numberPeople;

    @Schema(description = "状态", example = "1")
    private Byte status;



}
