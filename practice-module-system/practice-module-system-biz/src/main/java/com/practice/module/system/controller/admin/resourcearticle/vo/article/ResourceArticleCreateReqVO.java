package com.practice.module.system.controller.admin.resourcearticle.vo.article;

import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "管理后台 - 文章资源创建 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ResourceArticleCreateReqVO extends ResourceArticleBaseVO {
    @Schema(description = "用户编号", example = "25813")
    //@NotNull(message = "用户编号不能为空")
    private Long userId;

    //发布文章资源，初始状态为0
    @Schema(description = "状态", example = "0")
    private Byte status;

    //发布文章资源，初始浏览量为0
    @Schema(description = "浏览数量", example = "0")
    private Long viewCount;
}
