package com.practice.module.system.controller.admin.resourcearticle.vo.reject;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Date;

import static com.practice.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 文章资源驳回记录 Response VO")
@Data
@ToString(callSuper = true)
public class ResourceArticleRejectRespVO {

    @Schema(description = "编号", required = true, example = "25813")
    @NotNull(message = "编号不能为空")
    private Long id;

    @Schema(description = "文章编号", required = true, example = "25813")
    @NotNull(message = "文章编号不能为空")
    private Long articleId;

    @Schema(description = "创建驳回时间", required = true, example = "2022年1月23日")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime createTime;

    @Schema(description = "驳回意见", required = true, example = "错别字过多")
    @NotNull(message = "驳回意见不能为空")
    private String suggestion;
}
