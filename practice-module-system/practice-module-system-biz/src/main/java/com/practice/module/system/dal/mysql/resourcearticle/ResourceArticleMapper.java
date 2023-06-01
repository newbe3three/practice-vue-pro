package com.practice.module.system.dal.mysql.resourcearticle;

import java.util.*;

import com.practice.framework.common.pojo.PageResult;
import com.practice.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.practice.framework.mybatis.core.mapper.BaseMapperX;
import com.practice.module.system.controller.admin.resourcearticle.vo.article.ResourceArticleExportReqVO;
import com.practice.module.system.controller.admin.resourcearticle.vo.article.ResourceArticlePageReqVO;
import com.practice.module.system.dal.dataobject.resourcearticle.ResourceArticleDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 文章资源 Mapper
 *
 * @author n3
 */
@Mapper
public interface ResourceArticleMapper extends BaseMapperX<ResourceArticleDO> {

    default PageResult<ResourceArticleDO> selectPage(ResourceArticlePageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<ResourceArticleDO>()
                .eqIfPresent(ResourceArticleDO::getUserId, reqVO.getUserId())
                .eqIfPresent(ResourceArticleDO::getCategoryId, reqVO.getCategoryId())
                .likeIfPresent(ResourceArticleDO::getTitle, reqVO.getTitle())
                .likeIfPresent(ResourceArticleDO::getDescription, reqVO.getDescription())
                .likeIfPresent(ResourceArticleDO::getContent, reqVO.getContent())
                .eqIfPresent(ResourceArticleDO::getStatus, reqVO.getStatus())
                .eqIfPresent(ResourceArticleDO::getViewCount, reqVO.getViewCount())
                .betweenIfPresent(ResourceArticleDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(ResourceArticleDO::getId));
    }

    default List<ResourceArticleDO> selectList(ResourceArticleExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<ResourceArticleDO>()
                .eqIfPresent(ResourceArticleDO::getUserId, reqVO.getUserId())
                .eqIfPresent(ResourceArticleDO::getCategoryId, reqVO.getCategoryId())
                .eqIfPresent(ResourceArticleDO::getTitle, reqVO.getTitle())
                .eqIfPresent(ResourceArticleDO::getDescription, reqVO.getDescription())
                .eqIfPresent(ResourceArticleDO::getContent, reqVO.getContent())
                .eqIfPresent(ResourceArticleDO::getStatus, reqVO.getStatus())
                .eqIfPresent(ResourceArticleDO::getViewCount, reqVO.getViewCount())
                .betweenIfPresent(ResourceArticleDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(ResourceArticleDO::getId));
    }

}
