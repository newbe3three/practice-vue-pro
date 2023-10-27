package com.practice.module.system.controller.admin.signin.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.*;
import javax.validation.constraints.*;

import static com.practice.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 签到更新 Request VO")
@Data
@ToString(callSuper = true)
public class SignInUpdateReqVO {

    @Schema(description = "编号", required = true, example = "20541")
    @NotNull(message = "编号不能为空")
    private Long id;

    @Schema(description = "用户id", example = "12026")
    private Long userId;


    @Schema(description = "状态（默认为0， 已签到为1，已签退为2）", example = "2")
    private Byte status;

    @Schema(description = "实践id", required = true, example = "15288")
    @NotNull(message = "实践id不能为空")
    private Long practiceId;

    @Schema(description = "纬度", required = true)
    @NotNull(message = "纬度不能为空")
    private String latEnd;

    @Schema(description = "经度", required = true)
    @NotNull(message = "经度不能为空")
    private String lonEnd;

    @Schema(description = "签到时间", required = true)
//    @NotNull(message = "签到时间不能为空")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime signOutTime;

    @Schema(description = "签到地点", required = true)
    @NotNull(message = "签到地点不能为空")
    private String signOutLocation;

}
