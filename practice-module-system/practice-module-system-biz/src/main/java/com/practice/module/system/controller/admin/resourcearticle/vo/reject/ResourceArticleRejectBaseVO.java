package com.practice.module.system.controller.admin.resourcearticle.vo.reject;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ResourceArticleRejectBaseVO {
    @Schema(description = "文章编号", required = true, example = "25813")
    @NotNull(message = "文章编号不能为空")
    private Long articleId;

    @Schema(description = "驳回意见", required = true, example = "错别字过多")
    @NotNull(message = "驳回意见编号不能为空")
    private String  suggestion;

}
