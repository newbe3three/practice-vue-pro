package com.practice.module.system.service.resourcearticle;

import com.practice.module.system.dal.dataobject.resourcearticle.ResourceArticleRejectDO;
import com.practice.module.system.dal.mysql.resourcearticle.ResourceArticleRejectMapper;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.List;

/**
 * 文章资源审核驳回 Service 实现类
 *
 * @author n3
 */
@Service
@Validated
public class ResourceArticleRejectServiceImpl implements ResourceArticleRejectService{
    @Resource
    private ResourceArticleRejectMapper resourceArticleRejectMapper;
    @Override
    public List<ResourceArticleRejectDO> getResourceArticleRejectListByArticleId(Long articleId) {
        return resourceArticleRejectMapper.selectList("article_id", articleId);
    }
}
