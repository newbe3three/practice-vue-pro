package com.practice.module.system.service.resourcearticle;

import java.util.*;
import javax.validation.*;

import com.practice.module.system.controller.admin.resourcearticle.vo.article.*;
import com.practice.module.system.controller.admin.resourcearticle.vo.reject.ResourceArticleRejectCreateReqVO;
import com.practice.module.system.dal.dataobject.resourcearticle.ResourceArticleDO;
import com.practice.framework.common.pojo.PageResult;

/**
 * 文章资源 Service 接口
 *
 * @author n3
 */
public interface ResourceArticleService {

    /**
     * 创建文章资源
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createResourceArticle(@Valid ResourceArticleCreateReqVO createReqVO);

    /**
     * 更新文章资源
     *
     * @param updateReqVO 更新信息
     */
    void updateResourceArticle(@Valid ResourceArticleUpdateReqVO updateReqVO);

    /**
     * 删除文章资源
     *
     * @param id 编号
     */
    void deleteResourceArticle(Long id);

    /**
     * 获得文章资源
     *
     * @param id 编号
     * @return 文章资源
     */
    ResourceArticleDO getResourceArticle(Long id);

    /**
     * 获得文章资源列表
     *
     * @param ids 编号
     * @return 文章资源列表
     */
    List<ResourceArticleDO> getResourceArticleList(Collection<Long> ids);
    /**
     * 根据用户id查询资源列表
     *
     * @param userId 用户编号
     * @return 文章资源列表
     */
    //List<ResourceArticleDO> getResourceArticleListByUserId(Long userId);

    /**
     * 获得文章资源分页
     *
     * @param pageReqVO 分页查询
     * @return 文章资源分页
     */
    PageResult<ResourceArticleDO> getResourceArticlePage(ResourceArticlePageReqVO pageReqVO);

    /**
     * 获得文章资源列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 文章资源列表
     */
    List<ResourceArticleDO> getResourceArticleList(ResourceArticleExportReqVO exportReqVO);

    /**
     * 文章资源申请通过
     *
     * @param id 文章编号
     * @return
     */
    void reviewPassResourceArticle(Long id);

    /**
     * 文章资源申请驳回
     *
     * @param createReqVO 文章申请驳回意见以及文章编号
     * @return
     */
    void reviewFailureResourceArticle(ResourceArticleRejectCreateReqVO createReqVO);
}
