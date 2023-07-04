package com.practice.module.system.controller.admin.teachers.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
@Schema(description = "管理后台 - 导师信息 Response VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class TeacherRespVO  extends TeachersBaseVO {

    @Schema(description = "导师id", required = true, example = "19242")
    private Long id;

    @Schema(description = "部门名称", required = true, example = "赵六")
    @NotNull(message = "部门名称不能为空")
    private String deptName;

    @Schema(description = "创建时间", required = true)
    private LocalDateTime createTime;
}
