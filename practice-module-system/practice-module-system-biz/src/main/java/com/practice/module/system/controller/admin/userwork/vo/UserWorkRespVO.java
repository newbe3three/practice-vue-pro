package com.practice.module.system.controller.admin.userwork.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 工作经历 Response VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class UserWorkRespVO extends UserWorkBaseVO {

    @Schema(description = "编号", required = true, example = "16804")
    private Long id;

    @Schema(description = "创建时间", required = true)
    private LocalDateTime createTime;

}
