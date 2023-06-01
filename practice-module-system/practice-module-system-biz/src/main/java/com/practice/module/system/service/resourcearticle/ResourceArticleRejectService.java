package com.practice.module.system.service.resourcearticle;

import com.practice.module.system.controller.admin.resourcearticle.vo.article.ResourceArticleCreateReqVO;
import com.practice.module.system.dal.dataobject.resourcearticle.ResourceArticleDO;
import com.practice.module.system.dal.dataobject.resourcearticle.ResourceArticleRejectDO;

import javax.validation.Valid;
import java.util.List;

/**
 * 文章资源审核驳回 Service 接口
 *
 * @author n3
 */
public interface ResourceArticleRejectService {
    /**
     * 根据articleId查询驳回记录列表
     *
     * @param articleId 文章Id
     * @return 编号
     */
    List<ResourceArticleRejectDO> getResourceArticleRejectListByArticleId(Long articleId);
}
