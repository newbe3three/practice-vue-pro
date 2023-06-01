package com.practice.module.system.controller.admin.resourcearticle.vo.article;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.*;

@Schema(description = "管理后台 - 文章资源更新 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ResourceArticleUpdateReqVO extends ResourceArticleBaseVO {

    @Schema(description = "编号", required = true, example = "9270")
    @NotNull(message = "编号不能为空")
    private Long id;

}
