package com.practice.module.system.controller.admin.resourcearticle.vo.article;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import javax.validation.constraints.*;

/**
* 文章资源 Base VO，提供给添加、修改、详细的子 VO 使用
* 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
*/
@Data
public class ResourceArticleBaseVO {

    @Schema(description = "用户编号", required = true, example = "25813")
    //@NotNull(message = "用户编号不能为空")
    private Long userId;

    @Schema(description = "类别编号", required = true, example = "24503")
    @NotNull(message = "类别编号不能为空")
    private Long categoryId;

    @Schema(description = "文章标题", required = true)
    @NotNull(message = "文章标题不能为空")
    private String title;

    @Schema(description = "文章简介", example = "你说的对")
    private String description;

    @Schema(description = "文章内容")
    private String content;

    @Schema(description = "状态", example = "0")
    private Byte status;

    @Schema(description = "浏览数量", example = "22204")
    private Long viewCount;

}
