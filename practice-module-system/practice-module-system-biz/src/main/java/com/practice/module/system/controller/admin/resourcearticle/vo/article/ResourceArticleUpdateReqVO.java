package com.practice.module.system.controller.admin.resourcearticle.vo.article;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.*;

@Schema(description = "管理后台 - 文章资源更新 Request VO")
@Data
//@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ResourceArticleUpdateReqVO {

    @Schema(description = "编号", required = true, example = "9270")
    @NotNull(message = "编号不能为空")
    private Long id;

    @Schema(description = "类别编号", required = true, example = "24503")
    @NotNull(message = "类别编号不能为空")
    private Long categoryId;

    @Schema(description = "文章标题", required = true)
    @NotNull(message = "文章标题不能为空")
    private String title;

    @Schema(description = "文章简介", example = "你说的对")
    @NotNull(message = "文章标题不能为空")
    private String description;

    @Schema(description = "文章内容")
    @NotNull(message = "文章标题不能为空")
    private String content;
}
