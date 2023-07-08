package com.practice.module.system.controller.admin.practice.vo.practice;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 实践 Response VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class PracticeRespVO extends PracticeBaseVO {

    @Schema(description = "编号", required = true, example = "5549")
    private Long id;

    @Schema(description = "所属企业", example = "A企业")
    private String companyName;

    @Schema(description = "创建时间", required = true)
    private LocalDateTime createTime;



    @Schema(description = "状态", example = "1")
    private Byte status;
}
