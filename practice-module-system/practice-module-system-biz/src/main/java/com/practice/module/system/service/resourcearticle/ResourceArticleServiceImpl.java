package com.practice.module.system.service.resourcearticle;

import com.practice.module.system.controller.admin.resourcearticle.vo.article.*;
import com.practice.module.system.controller.admin.resourcearticle.vo.reject.ResourceArticleRejectCreateReqVO;
import com.practice.module.system.convert.resourcearticle.ResourceArticleRejectConvert;
import com.practice.module.system.dal.mysql.resourcearticle.ResourceArticleRejectMapper;
import com.practice.module.system.dal.mysql.user.AdminUserMapper;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import java.util.*;

import com.practice.module.system.dal.dataobject.resourcearticle.ResourceArticleDO;
import com.practice.framework.common.pojo.PageResult;

import com.practice.module.system.convert.resourcearticle.ResourceArticleConvert;
import com.practice.module.system.dal.mysql.resourcearticle.ResourceArticleMapper;

import static com.practice.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.practice.module.system.enums.ErrorCodeConstants.*;

/**
 * 文章资源 Service 实现类
 *
 * @author n3
 */
@Service
@Validated
public class ResourceArticleServiceImpl implements ResourceArticleService {

    @Resource
    private ResourceArticleMapper resourceArticleMapper;
    @Resource
    private ResourceArticleRejectMapper resourceArticleRejectMapper;
    @Resource
    private AdminUserMapper adminUserMapper;

    @Override
    public Long createResourceArticle(ResourceArticleCreateReqVO createReqVO) {
        //TODO：类别编号从数据库中获得

        // 插入
        ResourceArticleDO resourceArticle = ResourceArticleConvert.INSTANCE.convert(createReqVO);
        resourceArticleMapper.insert(resourceArticle);
        // 返回
        return resourceArticle.getId();
    }

    @Override
    public void updateResourceArticle(ResourceArticleUpdateReqVO updateReqVO) {
        // 校验存在
        validateResourceArticleExists(updateReqVO.getId());
        // 更新
        ResourceArticleDO updateObj = ResourceArticleConvert.INSTANCE.convert(updateReqVO);
        resourceArticleMapper.updateById(updateObj);
    }

    @Override
    public void deleteResourceArticle(Long id) {
        // 校验存在
        validateResourceArticleExists(id);
        // 删除
        resourceArticleMapper.deleteById(id);
    }

    private void validateResourceArticleExists(Long id) {
        if (resourceArticleMapper.selectById(id) == null) {
            throw exception(RESOURCE_ARTICLE_NOT_EXISTS);
        }
    }
    /**
     * 验证文章资源是状态是否为0（待审核）
     *  @param id 文章编号
     * **/
    private void validateResourceArticleStatus(Long id) {
        Byte status = resourceArticleMapper.selectById(id).getStatus();
        if (status!= 0 && status != 2) {
            //status != 0 || status != 2 文章驳回状态错误！
            throw exception(RESOURCE_ARTICLE_REVIEW_ERROR);
        }
    }
    @Override
    public ResourceArticleDO getResourceArticle(Long id) {
        return resourceArticleMapper.selectById(id);
    }

    @Override
    public List<ResourceArticleDO> getResourceArticleList(Collection<Long> ids) {
        return resourceArticleMapper.selectBatchIds(ids);
    }




    @Override
    public PageResult<ResourceArticleDO> getResourceArticlePage(ResourceArticlePageReqVO pageReqVO) {
        return resourceArticleMapper.selectPage(pageReqVO);
    }

    @Override
    public List<ResourceArticleDO> getResourceArticleList(ResourceArticleExportReqVO exportReqVO) {
        return resourceArticleMapper.selectList(exportReqVO);
    }

    @Override
    public void reviewPassResourceArticle(Long id) {
        // 校验存在
        validateResourceArticleExists(id);
        //校验状态
        validateResourceArticleStatus(id);
        //更新任务状态为已发布 status = 1
        ResourceArticleDO articleDO = new ResourceArticleDO();
        articleDO.setId(id);
        articleDO.setStatus((byte) 1);
        resourceArticleMapper.updateById(articleDO);
    }

    @Override
    public void reviewFailureResourceArticle(ResourceArticleRejectCreateReqVO createReqVO) {
        ResourceArticleDO articleDO = resourceArticleMapper.selectById(createReqVO.getArticleId());
        // 校验存在
        if (articleDO == null) {
            throw exception(RESOURCE_ARTICLE_NOT_EXISTS);
        }
        //校验状态 是否为0或2
        validateResourceArticleStatus(articleDO.getId());
        //1.更新任务状态为已驳回 status = 2
        articleDO.setStatus((byte) 2);
        resourceArticleMapper.updateById(articleDO);
        //2.文章驳回意见拆入表中
        resourceArticleRejectMapper.insert(ResourceArticleRejectConvert.INSTANCE.convert(createReqVO));

    }




}
