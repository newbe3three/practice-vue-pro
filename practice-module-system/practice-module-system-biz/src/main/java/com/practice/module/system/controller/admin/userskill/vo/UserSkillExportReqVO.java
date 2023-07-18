package com.practice.module.system.controller.admin.userskill.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import com.practice.framework.common.pojo.PageParam;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;

import static com.practice.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 个人技能 Excel 导出 Request VO，参数和 UserSkillPageReqVO 是一致的")
@Data
public class UserSkillExportReqVO {

    @Schema(description = "用户编号", example = "17603")
    private Long userId;

    @Schema(description = "技能")
    private String skill;

    @Schema(description = "技能等级")
    private String level;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}
