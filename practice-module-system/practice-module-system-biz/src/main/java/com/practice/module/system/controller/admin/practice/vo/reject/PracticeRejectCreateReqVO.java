package com.practice.module.system.controller.admin.practice.vo.reject;

import com.practice.module.system.controller.admin.resourcearticle.vo.reject.ResourceArticleRejectBaseVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Schema(description = "管理后台 - 实践驳回意见创建 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class PracticeRejectCreateReqVO extends PracticeRejectBaseVO {
}
