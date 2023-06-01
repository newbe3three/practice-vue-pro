package com.practice.module.system.dal.dataobject.resourcearticle;

import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.practice.framework.mybatis.core.dataobject.BaseDO;
import lombok.*;

/**
 * 文章资源驳回记录 DO
 *
 * @author n3
 */
@TableName("system_resource_article_reject")
@KeySequence("system_resource_article_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class ResourceArticleRejectDO extends BaseDO {
    /**
     * 编号
     */
    @TableId
    private Long id;
    /**
     * 文章资源编号
     */
    private Long articleId;

    /**
     * 驳回意见
     */
    private String suggestion;


}
