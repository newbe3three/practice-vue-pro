package com.practice.module.system.controller.admin.practiceapply.vo.apply;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 实践申请 Response VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class PracticeApplyRespVO extends PracticeApplyBaseVO {

    @Schema(description = "实践申请编号", required = true, example = "28491")
    private Long id;

    @Schema(description = "用户名称", example = "2")
    private String userNickName;

    @Schema(description = "实践名称", example = "2")
    private String practiceName;


    @Schema(description = "申请/创建时间", required = true)
    private LocalDateTime createTime;


    @Schema(description = "状态", example = "2")
    private Byte status;
    //todo:返回学生的信息
}
