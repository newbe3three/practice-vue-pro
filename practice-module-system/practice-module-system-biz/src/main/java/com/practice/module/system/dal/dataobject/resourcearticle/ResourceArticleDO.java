package com.practice.module.system.dal.dataobject.resourcearticle;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import com.practice.framework.mybatis.core.dataobject.BaseDO;

/**
 * 文章资源 DO
 *
 * @author n3
 */
@TableName("system_resource_article")
@KeySequence("system_resource_article_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResourceArticleDO extends BaseDO {

    /**
     * 编号
     */
    @TableId
    private Long id;
    /**
     * 用户编号
     */
    private Long userId;
    /**
     * 类别编号
     */
    private Long categoryId;
    /**
     * 文章标题
     */
    private String title;
    /**
     * 文章简介
     */
    private String description;
    /**
     * 文章内容
     */
    private String content;
    /**
     * 状态
     *
     * 枚举 {@link TODO resource_article 对应的类}
     */
    private Byte status;
    /**
     * 浏览数量
     */
    private Long viewCount;


}
