package com.practice.module.system.controller.admin.user.vo.uuid;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;


@Data
@EqualsAndHashCode(callSuper = true)
public class UUIDCreateReqVO extends UUIDBaseVO{

//    @Schema(description = "用户账号", required = true, example = "practice")
//    @NotBlank(message = "用户账号不能为空")
//    private String username;
//
//    @Schema(description = "uuid",example = "adsadada")
//    private String uuid;

}
