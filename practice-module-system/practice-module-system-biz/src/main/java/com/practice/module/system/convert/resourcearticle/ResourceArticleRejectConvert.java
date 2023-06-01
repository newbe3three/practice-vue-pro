package com.practice.module.system.convert.resourcearticle;

import com.practice.module.system.controller.admin.resourcearticle.vo.reject.ResourceArticleRejectCreateReqVO;
import com.practice.module.system.controller.admin.resourcearticle.vo.reject.ResourceArticleRejectRespVO;
import com.practice.module.system.dal.dataobject.resourcearticle.ResourceArticleRejectDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 文章资源驳回记录 Convert
 *
 * @author n3
 */
@Mapper
public interface ResourceArticleRejectConvert {
    ResourceArticleRejectConvert INSTANCE = Mappers.getMapper(ResourceArticleRejectConvert.class);

    ResourceArticleRejectDO convert(ResourceArticleRejectCreateReqVO bean);

    ResourceArticleRejectRespVO convert(ResourceArticleRejectDO bean);

    List<ResourceArticleRejectRespVO> convertList(List<ResourceArticleRejectDO> list);
}
