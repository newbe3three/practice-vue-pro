package com.practice.module.system.controller.admin.resourcearticle.vo.article;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Schema(description = "管理后台 - 文章审核 Response VO")
@Data
@ToString(callSuper = true)
public class ResourceArticleReviewVO {
    @Schema(description = "文章编号", required = true, example = "9270")
    @NotNull(message = "文章编号不能为空！")
    private Long articleId;

    @Schema(description = "驳回意见", required = true, example = "错别字过多")
    @NotNull(message = "驳回意见不能为空！")
    private String suggestion;
}
