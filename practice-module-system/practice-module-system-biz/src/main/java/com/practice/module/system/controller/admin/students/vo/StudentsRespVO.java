package com.practice.module.system.controller.admin.students.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 学生信息 Response VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class StudentsRespVO extends StudentsBaseVO {

    @Schema(description = "学生id", required = true, example = "753")
    private Long id;

    @Schema(description = "创建时间", required = true)
    private LocalDateTime createTime;

}
