package com.practice.module.system.controller.admin.resourcearticle.vo.reject;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Schema(description = "管理后台 - 文章资源驳回意见创建 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ResourceArticleRejectCreateReqVO extends ResourceArticleRejectBaseVO{
}
