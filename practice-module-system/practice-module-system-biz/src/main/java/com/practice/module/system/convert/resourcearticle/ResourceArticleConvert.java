package com.practice.module.system.convert.resourcearticle;

import java.util.*;

import com.practice.framework.common.pojo.PageResult;

import com.practice.module.system.controller.admin.resourcearticle.vo.article.*;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import com.practice.module.system.dal.dataobject.resourcearticle.ResourceArticleDO;

/**
 * 文章资源 Convert
 *
 * @author n3
 */
@Mapper
public interface ResourceArticleConvert {

    ResourceArticleConvert INSTANCE = Mappers.getMapper(ResourceArticleConvert.class);

    ResourceArticleDO convert(ResourceArticleCreateReqVO bean);

    ResourceArticleDO convert(ResourceArticleUpdateReqVO bean);

    ResourceArticleRespVO convert(ResourceArticleDO bean);

    List<ResourceArticleRespVO> convertList(List<ResourceArticleDO> list);

    PageResult<ResourceArticleRespVO> convertPage(PageResult<ResourceArticleDO> page);

    List<ResourceArticleExcelVO> convertList02(List<ResourceArticleDO> list);

   // ResourceArticleDO convert(ResourceArticleReviewVO bean);

}
