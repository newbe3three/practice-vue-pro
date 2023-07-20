package com.practice.module.system.controller.admin.students.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import javax.validation.constraints.*;

import static com.practice.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

/**
* 学生信息 Base VO，提供给添加、修改、详细的子 VO 使用
* 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
*/
@Data
public class StudentsBaseVO {

    @Schema(description = "用户id", example = "200")
    @NotNull(message = "用户id不能为空")
    private Long userId;

    @Schema(description = "姓名", required = true, example = "赵六")
    @NotNull(message = "姓名不能为空")
    private String name;

    @Schema(description = "部门ID", example = "27074")
    @NotNull(message = "部门id不能为空")
    private Long deptId;

    @Schema(description = "指导教师id", example = "18510")
    private Long teacherId;

    @Schema(description = "所属专业", required = true)
    @NotNull(message = "所属专业不能为空")
    private String major;

    @Schema(description = "手机号码")
    private String mobile;

    @Schema(description = "身份证号", required = true, example = "19565")
    @NotNull(message = "身份证号不能为空")
    private String cardId;

    @Schema(description = "开始时间")
    @NotNull(message = "身份证号不能为空")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime startTime;

    @Schema(description = "结束时间")
    @NotNull(message = "身份证号不能为空")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime endTime;

    @Schema(description = "校内经历")
    private String experience;

}
