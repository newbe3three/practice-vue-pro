package com.practice.module.system.controller.admin.practiceapply.vo.apply;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import com.practice.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static com.practice.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 实践申请分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class PracticeApplyPageReqVO extends PageParam {

    @Schema(description = "用户id", example = "31785")
    private Long userId;

    @Schema(description = "简历url")
    private String resume;

    @Schema(description = "附加信息")
    private String message;

    @Schema(description = "状态", example = "2")
    private Byte status;

    @Schema(description = "申请/创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "申请实践id", example = "30503")
    private Long practiceId;


    @Schema(description = "实践id列表", example = "30503")
    private List<Long> practiceIdList;
}
