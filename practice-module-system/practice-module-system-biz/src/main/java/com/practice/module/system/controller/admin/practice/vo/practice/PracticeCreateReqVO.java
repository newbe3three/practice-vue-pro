package com.practice.module.system.controller.admin.practice.vo.practice;

import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "管理后台 - 实践创建 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class PracticeCreateReqVO extends PracticeBaseVO {

}
