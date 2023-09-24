package com.practice.module.system.controller.admin.signin.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import com.practice.framework.common.pojo.PageParam;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;

import static com.practice.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 签到 Excel 导出 Request VO，参数和 SignInPageReqVO 是一致的")
@Data
public class SignInExportReqVO {

    @Schema(description = "用户id", example = "12026")
    private Long userId;

    @Schema(description = "实践id", example = "15288")
    private Long practiceId;

    @Schema(description = "状态（默认为0， 已签到为1，已签退为2）", example = "2")
    private Byte status;

    @Schema(description = "签到结果（默认为0，迟到为1，早退2，迟到且早退为3，正常签到为4）")
    private Byte result;

    @Schema(description = "签到地点")
    private String signInLocation;

    @Schema(description = "签退地点")
    private String signOutLocation;

    @Schema(description = "申请/创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}
