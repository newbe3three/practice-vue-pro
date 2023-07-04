package com.practice.module.system.controller.admin.students.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 学生信息更新 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class StudentsUpdateReqVO extends StudentsBaseVO {

    @Schema(description = "学生id", required = true, example = "753")
    @NotNull(message = "学生id不能为空")
    private Long id;

}
