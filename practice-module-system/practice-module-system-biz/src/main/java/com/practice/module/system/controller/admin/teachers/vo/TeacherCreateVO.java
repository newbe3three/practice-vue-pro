package com.practice.module.system.controller.admin.teachers.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class TeacherCreateVO {

    @Schema(description = "用户id", example = "19769")
    @NotNull(message = "用户id不能为空")
    private Long userId;




    @Schema(description = "所属专业", required = true)
    @NotNull(message = "所属专业不能为空")
    private String major;



    @Schema(description = "身份证号", required = true, example = "11033")
    @NotNull(message = "身份证号不能为空")
    private String cardId;
}
