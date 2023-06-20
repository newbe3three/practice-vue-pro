package com.practice.module.system.controller.admin.practice.vo.reject;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Schema(description = "管理后台 - 实践驳回记录 Response VO")
@Data

@ToString(callSuper = true)
public class PracticeRejectBaseVO {
    @Schema(description = "实践编号", required = true, example = "25813")
    @NotNull(message = "实践编号不能为空")
    private Long practiceId;

    @Schema(description = "驳回意见", required = true, example = "错别字过多")
    @NotNull(message = "驳回意见编号不能为空")
    private String  suggestion;



}
