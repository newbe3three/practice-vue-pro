package com.practice.module.system.controller.admin.practiceschool.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 学校申请实践 Response VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class PracticeSchoolRespVO extends PracticeSchoolBaseVO {

    @Schema(description = "编号", required = true, example = "26607")
    private Long id;

    @Schema(description = "申请/创建时间", required = true)
    private LocalDateTime createTime;

    @Schema(description = "状态", example = "2")
    private Byte status;
}
