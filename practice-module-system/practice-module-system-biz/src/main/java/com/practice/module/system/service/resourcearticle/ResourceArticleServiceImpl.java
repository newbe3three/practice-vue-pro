package com.practice.module.system.service.resourcearticle;

import com.practice.module.system.controller.admin.resourcearticle.vo.article.*;
import com.practice.module.system.controller.admin.resourcearticle.vo.reject.ResourceArticleRejectCreateReqVO;
import com.practice.module.system.convert.resourcearticle.ResourceArticleRejectConvert;
import com.practice.module.system.dal.dataobject.resourcearticle.ResourceArticleRejectDO;
import com.practice.module.system.dal.dataobject.resourcecategory.ResourceCategoryDO;
import com.practice.module.system.dal.mysql.resourcearticle.ResourceArticleRejectMapper;
import com.practice.module.system.dal.mysql.resourcecategory.ResourceCategoryMapper;
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
    private ResourceCategoryMapper resourceCategoryMapper;
    @Resource
    private AdminUserMapper adminUserMapper;

    @Override
    public Long createResourceArticle(ResourceArticleCreateReqVO createReqVO) {
        //验证类别id是否存在
        validateResourceCategoryExists(createReqVO.getCategoryId());
        // 插入
        ResourceArticleDO resourceArticle = ResourceArticleConvert.INSTANCE.convert(createReqVO);
        resourceArticleMapper.insert(resourceArticle);
        // 返回
        return resourceArticle.getId();
    }

    @Override
    public void updateResourceArticle(ResourceArticleUpdateReqVO updateReqVO) {
        //验证类别id是否存在
        validateResourceCategoryExists(updateReqVO.getCategoryId());
        // 校验存在
        validateResourceArticleExists(updateReqVO.getId());

        ResourceArticleDO updateObj = ResourceArticleConvert.INSTANCE.convert(updateReqVO);
        //变更文章状态为待审核 0
        updateObj.setStatus((byte) 0);
       // // 根据文章编号更细内容
        resourceArticleMapper.updateById(updateObj);
    }

    @Override
    public void deleteResourceArticle(Long id) {
        // 校验存在
        validateResourceArticleExists(id);
        // 删除
        resourceArticleMapper.deleteById(id);
    }

    private void validateResourceCategoryExists(Long categoryId) {
        if (resourceCategoryMapper.selectById(categoryId) == null) {
            throw exception(RESOURCE_CATEGORY_NOT_EXISTS);
        }

    }


    private void validateResourceArticleExists(Long id) {
        if (resourceArticleMapper.selectById(id) == null) {
            throw exception(RESOURCE_ARTICLE_NOT_EXISTS);
        }
    }
    /**
     * 验证文章资源是状态 是不是0或者2
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
        //更新资源状态为已发布 status = 1
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
        //1.更新资源状态为已驳回 status = 2
        articleDO.setStatus((byte) 2);
        resourceArticleMapper.updateById(articleDO);
        //2.文章驳回意见拆入表中
        resourceArticleRejectMapper.insert(ResourceArticleRejectConvert.INSTANCE.convert(createReqVO));

    }

    @Override
    public void takeDownResourceArticle(Long id) {
        // 根据id查询
        ResourceArticleDO articleDO = resourceArticleMapper.selectById(id);
        //验证存在（不用上面的方法是因为，减少数据库查询次数）
       if(articleDO == null) {
           throw exception(RESOURCE_ARTICLE_NOT_EXISTS);
       }
        //验证文章状态：只有状态为 1 的文章可以进行下架操作
        if (articleDO.getStatus() != 1) {
            throw exception(RESOURCE_ARTICLE_TAKE_DOWN_ERROR);
        }
        //更新文章状态 已发布 --》待审核 1 --》0
        articleDO.setStatus((byte) 0);
        resourceArticleMapper.updateById(articleDO);
    }


}
