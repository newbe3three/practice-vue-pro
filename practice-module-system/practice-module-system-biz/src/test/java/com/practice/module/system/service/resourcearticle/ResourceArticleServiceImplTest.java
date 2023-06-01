package com.practice.module.system.service.resourcearticle;

import com.practice.module.system.controller.admin.resourcearticle.vo.article.ResourceArticleCreateReqVO;
import com.practice.module.system.controller.admin.resourcearticle.vo.article.ResourceArticleExportReqVO;
import com.practice.module.system.controller.admin.resourcearticle.vo.article.ResourceArticlePageReqVO;
import com.practice.module.system.controller.admin.resourcearticle.vo.article.ResourceArticleUpdateReqVO;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import javax.annotation.Resource;

import com.practice.framework.test.core.ut.BaseDbUnitTest;

import com.practice.module.system.dal.dataobject.resourcearticle.ResourceArticleDO;
import com.practice.module.system.dal.mysql.resourcearticle.ResourceArticleMapper;
import com.practice.framework.common.pojo.PageResult;

import org.springframework.context.annotation.Import;
import java.util.*;

import static com.practice.module.system.enums.ErrorCodeConstants.*;
import static com.practice.framework.test.core.util.AssertUtils.*;
import static com.practice.framework.test.core.util.RandomUtils.*;
import static com.practice.framework.common.util.date.LocalDateTimeUtils.*;
import static com.practice.framework.common.util.object.ObjectUtils.*;
import static org.junit.jupiter.api.Assertions.*;

/**
* {@link ResourceArticleServiceImpl} 的单元测试类
*
* @author n3
*/
@Import(ResourceArticleServiceImpl.class)
public class ResourceArticleServiceImplTest extends BaseDbUnitTest {

    @Resource
    private ResourceArticleServiceImpl resourceArticleService;

    @Resource
    private ResourceArticleMapper resourceArticleMapper;

    @Test
    public void testCreateResourceArticle_success() {
        // 准备参数
        ResourceArticleCreateReqVO reqVO = randomPojo(ResourceArticleCreateReqVO.class);

        // 调用
        Long resourceArticleId = resourceArticleService.createResourceArticle(reqVO);
        // 断言
        assertNotNull(resourceArticleId);
        // 校验记录的属性是否正确
        ResourceArticleDO resourceArticle = resourceArticleMapper.selectById(resourceArticleId);
        assertPojoEquals(reqVO, resourceArticle);
    }

    @Test
    public void testUpdateResourceArticle_success() {
        // mock 数据
        ResourceArticleDO dbResourceArticle = randomPojo(ResourceArticleDO.class);
        resourceArticleMapper.insert(dbResourceArticle);// @Sql: 先插入出一条存在的数据
        // 准备参数
        ResourceArticleUpdateReqVO reqVO = randomPojo(ResourceArticleUpdateReqVO.class, o -> {
            o.setId(dbResourceArticle.getId()); // 设置更新的 ID
        });

        // 调用
        resourceArticleService.updateResourceArticle(reqVO);
        // 校验是否更新正确
        ResourceArticleDO resourceArticle = resourceArticleMapper.selectById(reqVO.getId()); // 获取最新的
        assertPojoEquals(reqVO, resourceArticle);
    }

    @Test
    public void testUpdateResourceArticle_notExists() {
        // 准备参数
        ResourceArticleUpdateReqVO reqVO = randomPojo(ResourceArticleUpdateReqVO.class);

        // 调用, 并断言异常
        assertServiceException(() -> resourceArticleService.updateResourceArticle(reqVO), RESOURCE_ARTICLE_NOT_EXISTS);
    }

    @Test
    public void testDeleteResourceArticle_success() {
        // mock 数据
        ResourceArticleDO dbResourceArticle = randomPojo(ResourceArticleDO.class);
        resourceArticleMapper.insert(dbResourceArticle);// @Sql: 先插入出一条存在的数据
        // 准备参数
        Long id = dbResourceArticle.getId();

        // 调用
        resourceArticleService.deleteResourceArticle(id);
       // 校验数据不存在了
       assertNull(resourceArticleMapper.selectById(id));
    }

    @Test
    public void testDeleteResourceArticle_notExists() {
        // 准备参数
        Long id = randomLongId();

        // 调用, 并断言异常
        assertServiceException(() -> resourceArticleService.deleteResourceArticle(id), RESOURCE_ARTICLE_NOT_EXISTS);
    }

    @Test
    @Disabled  // TODO 请修改 null 为需要的值，然后删除 @Disabled 注解
    public void testGetResourceArticlePage() {
       // mock 数据
       ResourceArticleDO dbResourceArticle = randomPojo(ResourceArticleDO.class, o -> { // 等会查询到
           o.setUserId(null);
           o.setCategoryId(null);
           o.setTitle(null);
           o.setDescription(null);
           o.setContent(null);
           o.setStatus(null);
           o.setViewCount(null);
           o.setCreateTime(null);
       });
       resourceArticleMapper.insert(dbResourceArticle);
       // 测试 userId 不匹配
       resourceArticleMapper.insert(cloneIgnoreId(dbResourceArticle, o -> o.setUserId(null)));
       // 测试 categoryId 不匹配
       resourceArticleMapper.insert(cloneIgnoreId(dbResourceArticle, o -> o.setCategoryId(null)));
       // 测试 title 不匹配
       resourceArticleMapper.insert(cloneIgnoreId(dbResourceArticle, o -> o.setTitle(null)));
       // 测试 description 不匹配
       resourceArticleMapper.insert(cloneIgnoreId(dbResourceArticle, o -> o.setDescription(null)));
       // 测试 content 不匹配
       resourceArticleMapper.insert(cloneIgnoreId(dbResourceArticle, o -> o.setContent(null)));
       // 测试 status 不匹配
       resourceArticleMapper.insert(cloneIgnoreId(dbResourceArticle, o -> o.setStatus(null)));
       // 测试 viewCount 不匹配
       resourceArticleMapper.insert(cloneIgnoreId(dbResourceArticle, o -> o.setViewCount(null)));
       // 测试 createTime 不匹配
       resourceArticleMapper.insert(cloneIgnoreId(dbResourceArticle, o -> o.setCreateTime(null)));
       // 准备参数
       ResourceArticlePageReqVO reqVO = new ResourceArticlePageReqVO();
       reqVO.setUserId(null);
       reqVO.setCategoryId(null);
       reqVO.setTitle(null);
       reqVO.setDescription(null);
       reqVO.setContent(null);
       reqVO.setStatus(null);
       reqVO.setViewCount(null);
       reqVO.setCreateTime(buildBetweenTime(2023, 2, 1, 2023, 2, 28));

       // 调用
       PageResult<ResourceArticleDO> pageResult = resourceArticleService.getResourceArticlePage(reqVO);
       // 断言
       assertEquals(1, pageResult.getTotal());
       assertEquals(1, pageResult.getList().size());
       assertPojoEquals(dbResourceArticle, pageResult.getList().get(0));
    }

    @Test
    @Disabled  // TODO 请修改 null 为需要的值，然后删除 @Disabled 注解
    public void testGetResourceArticleList() {
       // mock 数据
       ResourceArticleDO dbResourceArticle = randomPojo(ResourceArticleDO.class, o -> { // 等会查询到
           o.setUserId(null);
           o.setCategoryId(null);
           o.setTitle(null);
           o.setDescription(null);
           o.setContent(null);
           o.setStatus(null);
           o.setViewCount(null);
           o.setCreateTime(null);
       });
       resourceArticleMapper.insert(dbResourceArticle);
       // 测试 userId 不匹配
       resourceArticleMapper.insert(cloneIgnoreId(dbResourceArticle, o -> o.setUserId(null)));
       // 测试 categoryId 不匹配
       resourceArticleMapper.insert(cloneIgnoreId(dbResourceArticle, o -> o.setCategoryId(null)));
       // 测试 title 不匹配
       resourceArticleMapper.insert(cloneIgnoreId(dbResourceArticle, o -> o.setTitle(null)));
       // 测试 description 不匹配
       resourceArticleMapper.insert(cloneIgnoreId(dbResourceArticle, o -> o.setDescription(null)));
       // 测试 content 不匹配
       resourceArticleMapper.insert(cloneIgnoreId(dbResourceArticle, o -> o.setContent(null)));
       // 测试 status 不匹配
       resourceArticleMapper.insert(cloneIgnoreId(dbResourceArticle, o -> o.setStatus(null)));
       // 测试 viewCount 不匹配
       resourceArticleMapper.insert(cloneIgnoreId(dbResourceArticle, o -> o.setViewCount(null)));
       // 测试 createTime 不匹配
       resourceArticleMapper.insert(cloneIgnoreId(dbResourceArticle, o -> o.setCreateTime(null)));
       // 准备参数
       ResourceArticleExportReqVO reqVO = new ResourceArticleExportReqVO();
       reqVO.setUserId(null);
       reqVO.setCategoryId(null);
       reqVO.setTitle(null);
       reqVO.setDescription(null);
       reqVO.setContent(null);
       reqVO.setStatus(null);
       reqVO.setViewCount(null);
       reqVO.setCreateTime(buildBetweenTime(2023, 2, 1, 2023, 2, 28));

       // 调用
       List<ResourceArticleDO> list = resourceArticleService.getResourceArticleList(reqVO);
       // 断言
       assertEquals(1, list.size());
       assertPojoEquals(dbResourceArticle, list.get(0));
    }

}
