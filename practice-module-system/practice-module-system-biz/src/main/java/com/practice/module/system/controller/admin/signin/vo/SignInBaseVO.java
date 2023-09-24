package com.practice.module.system.controller.admin.signin.vo;

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
* 签到 Base VO，提供给添加、修改、详细的子 VO 使用
* 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
*/
@Data
public class SignInBaseVO {

    @Schema(description = "用户id", example = "12026")
    private Long userId;

    @Schema(description = "实践id", required = true, example = "15288")
    @NotNull(message = "实践id不能为空")
    private Long practiceId;

    @Schema(description = "状态（默认为0， 已签到为1，已签退为2）", example = "2")
    private Byte status;

    @Schema(description = "签到结果（默认为0，迟到为1，早退2，迟到且早退为3，正常签到为4）")
    private Byte result;

    @Schema(description = "纬度", required = true)
    @NotNull(message = "纬度不能为空")
    private String lat;

    @Schema(description = "经度", required = true)
    @NotNull(message = "经度不能为空")
    private String lon;

    @Schema(description = "签到时间", required = true)
    @NotNull(message = "签到时间不能为空")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime signInTime;

    @Schema(description = "签退时间", required = true)
    @NotNull(message = "签退时间不能为空")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime signOutTime;

    @Schema(description = "签到地点", required = true)
    @NotNull(message = "签到地点不能为空")
    private String signInLocation;

    @Schema(description = "签退地点", required = true)
    @NotNull(message = "签退地点不能为空")
    private String signOutLocation;

}
