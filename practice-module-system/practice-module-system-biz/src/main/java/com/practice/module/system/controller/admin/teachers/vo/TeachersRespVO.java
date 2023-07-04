package com.practice.module.system.controller.admin.teachers.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 导师信息 Response VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class TeachersRespVO extends TeachersBaseVO {

    @Schema(description = "导师id", required = true, example = "19242")
    private Long id;

    @Schema(description = "创建时间", required = true)
    private LocalDateTime createTime;

}
