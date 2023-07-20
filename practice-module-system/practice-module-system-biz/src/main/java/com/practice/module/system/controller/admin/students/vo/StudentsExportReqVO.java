package com.practice.module.system.controller.admin.students.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import com.practice.framework.common.pojo.PageParam;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;

import static com.practice.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 学生信息 Excel 导出 Request VO，参数和 StudentsPageReqVO 是一致的")
@Data
public class StudentsExportReqVO {

    @Schema(description = "用户id", example = "200")
    private Long userId;

    @Schema(description = "姓名", example = "赵六")
    private String name;

    @Schema(description = "部门ID", example = "27074")
    private Long deptId;

    @Schema(description = "指导教师id", example = "18510")
    private Long teacherId;

    @Schema(description = "所属专业")
    private String major;

    @Schema(description = "手机号码")
    private String mobile;

    @Schema(description = "身份证号", example = "19565")
    private String cardId;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "开始时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] startTime;

    @Schema(description = "结束时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] endTime;

    @Schema(description = "校内经历")
    private String experience;

}
