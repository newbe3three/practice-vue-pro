package com.practice.module.system.controller.admin.userwork.vo;

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
* 工作经历 Base VO，提供给添加、修改、详细的子 VO 使用
* 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
*/
@Data
public class UserWorkBaseVO {

    @Schema(description = "用户编号", required = true, example = "23512")
   // @NotNull(message = "用户编号不能为空")
    private Long userId;

    @Schema(description = "职位", required = true, example = "赵六")
    @NotNull(message = "职位不能为空")
    private String name;

    @Schema(description = "类型", required = true, example = "全职")
    @NotNull(message = "类型不能为空")
    private String type;

    @Schema(description = "公司名称", required = true, example = "王五")
    @NotNull(message = "公司名称不能为空")
    private String companyName;

    @Schema(description = "描述", required = true, example = "随便")
    @NotNull(message = "描述不能为空")
    private String description;

    @Schema(description = "开始时间", required = true)
    @NotNull(message = "开始时间不能为空")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime startTime;

    @Schema(description = "结束时间", required = true)
    @NotNull(message = "结束时间不能为空")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime endTime;

}
