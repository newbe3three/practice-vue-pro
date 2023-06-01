package com.practice.module.system.controller.admin.resourcearticle.vo.article;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;

import com.alibaba.excel.annotation.ExcelProperty;
import com.practice.framework.excel.core.annotations.DictFormat;
import com.practice.framework.excel.core.convert.DictConvert;


/**
 * 文章资源 Excel VO
 *
 * @author n3
 */
@Data
public class ResourceArticleExcelVO {

    @ExcelProperty("编号")
    private Long id;

    @ExcelProperty("用户编号")
    private Long userId;

    @ExcelProperty("类别编号")
    private Long categoryId;

    @ExcelProperty("文章标题")
    private String title;

    @ExcelProperty("文章简介")
    private String description;

    @ExcelProperty("文章内容")
    private String content;

    @ExcelProperty(value = "状态", converter = DictConvert.class)
    @DictFormat("resource_article") // TODO 代码优化：建议设置到对应的 XXXDictTypeConstants 枚举类中
    private Byte status;
/**
 * //驳回记录可能有多条
   @ExcelProperty(value = "驳回意见", converter = DictConvert.class)
   @DictFormat("resource_article") // TODO 代码优化：建议设置到对应的 XXXDictTypeConstants 枚举类中
   private String suggestion;
**/

    @ExcelProperty("浏览数量")
    private Long viewCount;

    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}
