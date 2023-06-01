package com.practice.module.system.controller.admin.resourcearticle.vo.article;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import com.practice.framework.common.pojo.PageParam;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;

import static com.practice.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 文章资源 Excel 导出 Request VO，参数和 ResourceArticlePageReqVO 是一致的")
@Data
public class ResourceArticleExportReqVO {

    @Schema(description = "用户编号", example = "25813")
    private Long userId;

    @Schema(description = "类别编号", example = "24503")
    private Long categoryId;

    @Schema(description = "文章标题")
    private String title;

    @Schema(description = "文章简介", example = "你说的对")
    private String description;

    @Schema(description = "文章内容")
    private String content;

    @Schema(description = "状态", example = "1")
    private Byte status;

    /**
     * //驳回记录可能有多条
     @ExcelProperty(value = "驳回意见", converter = DictConvert.class)
     @DictFormat("resource_article") // TODO 代码优化：建议设置到对应的 XXXDictTypeConstants 枚举类中
     private String suggestion;
     **/

    @Schema(description = "浏览数量", example = "22204")
    private Long viewCount;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}
