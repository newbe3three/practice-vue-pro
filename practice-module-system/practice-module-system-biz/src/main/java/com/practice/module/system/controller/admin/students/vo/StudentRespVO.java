package com.practice.module.system.controller.admin.students.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
@Schema(description = "管理后台 - 学生信息 Response VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class StudentRespVO extends StudentsBaseVO{

    @Schema(description = "学生id", required = true, example = "753")
    private Long id;

    @Schema(description = "部门名称", required = true, example = "赵六")
    @NotNull(message = "部门名称不能为空")
    private String deptName;

    @Schema(description = "导师姓名", required = true, example = "赵六")
    @NotNull(message = "导师姓名不能为空")
    private String teacherName;

    @Schema(description = "创建时间", required = true)
    private LocalDateTime createTime;
}
