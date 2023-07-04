package com.practice.module.system.controller.admin.teachers.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import com.practice.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static com.practice.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 导师信息分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class TeachersPageReqVO extends PageParam {

    @Schema(description = "用户id", example = "19769")
    private Long userId;

    @Schema(description = "姓名", example = "张三")
    private String name;

    @Schema(description = "部门ID", example = "29346")
    private Long deptId;

    @Schema(description = "所属专业")
    private String major;

    @Schema(description = "手机号码")
    private String mobile;

    @Schema(description = "身份证号", example = "11033")
    private String cardId;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}
