package com.practice.module.system.service.organizationother;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

import javax.annotation.Resource;

import com.practice.framework.test.core.ut.BaseDbUnitTest;

import com.practice.module.system.controller.admin.organizationother.vo.*;
import com.practice.module.system.dal.dataobject.organizationother.OrganizationOtherDO;
import com.practice.module.system.dal.mysql.organizationother.OrganizationOtherMapper;
import com.practice.framework.common.pojo.PageResult;

import javax.annotation.Resource;
import org.springframework.context.annotation.Import;
import java.util.*;
import java.time.LocalDateTime;

import static cn.hutool.core.util.RandomUtil.*;
import static com.practice.module.system.enums.ErrorCodeConstants.*;
import static com.practice.framework.test.core.util.AssertUtils.*;
import static com.practice.framework.test.core.util.RandomUtils.*;
import static com.practice.framework.common.util.date.LocalDateTimeUtils.*;
import static com.practice.framework.common.util.object.ObjectUtils.*;
import static com.practice.framework.common.util.date.DateUtils.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
* {@link OrganizationOtherServiceImpl} 的单元测试类
*
* @author wx
*/
@Import(OrganizationOtherServiceImpl.class)
public class OrganizationOtherServiceImplTest extends BaseDbUnitTest {

    @Resource
    private OrganizationOtherServiceImpl organizationOtherService;

    @Resource
    private OrganizationOtherMapper organizationOtherMapper;

    @Test
    public void testCreateOrganizationOther_success() {
        // 准备参数
        OrganizationOtherCreateReqVO reqVO = randomPojo(OrganizationOtherCreateReqVO.class);

        // 调用
        Long organizationOtherId = organizationOtherService.createOrganizationOther(reqVO);
        // 断言
        assertNotNull(organizationOtherId);
        // 校验记录的属性是否正确
        OrganizationOtherDO organizationOther = organizationOtherMapper.selectById(organizationOtherId);
        assertPojoEquals(reqVO, organizationOther);
    }

    @Test
    public void testUpdateOrganizationOther_success() {
        // mock 数据
        OrganizationOtherDO dbOrganizationOther = randomPojo(OrganizationOtherDO.class);
        organizationOtherMapper.insert(dbOrganizationOther);// @Sql: 先插入出一条存在的数据
        // 准备参数
        OrganizationOtherUpdateReqVO reqVO = randomPojo(OrganizationOtherUpdateReqVO.class, o -> {
            o.setId(dbOrganizationOther.getId()); // 设置更新的 ID
        });

        // 调用
        organizationOtherService.updateOrganizationOther(reqVO);
        // 校验是否更新正确
        OrganizationOtherDO organizationOther = organizationOtherMapper.selectById(reqVO.getId()); // 获取最新的
        assertPojoEquals(reqVO, organizationOther);
    }

    @Test
    public void testUpdateOrganizationOther_notExists() {
        // 准备参数
        OrganizationOtherUpdateReqVO reqVO = randomPojo(OrganizationOtherUpdateReqVO.class);

        // 调用, 并断言异常
        assertServiceException(() -> organizationOtherService.updateOrganizationOther(reqVO), ORGANIZATION_OTHER_NOT_EXISTS);
    }

    @Test
    public void testDeleteOrganizationOther_success() {
        // mock 数据
        OrganizationOtherDO dbOrganizationOther = randomPojo(OrganizationOtherDO.class);
        organizationOtherMapper.insert(dbOrganizationOther);// @Sql: 先插入出一条存在的数据
        // 准备参数
        Long id = dbOrganizationOther.getId();

        // 调用
        organizationOtherService.deleteOrganizationOther(id);
       // 校验数据不存在了
       assertNull(organizationOtherMapper.selectById(id));
    }

    @Test
    public void testDeleteOrganizationOther_notExists() {
        // 准备参数
        Long id = randomLongId();

        // 调用, 并断言异常
        assertServiceException(() -> organizationOtherService.deleteOrganizationOther(id), ORGANIZATION_OTHER_NOT_EXISTS);
    }

    @Test
    @Disabled  // TODO 请修改 null 为需要的值，然后删除 @Disabled 注解
    public void testGetOrganizationOtherPage() {
       // mock 数据
       OrganizationOtherDO dbOrganizationOther = randomPojo(OrganizationOtherDO.class, o -> { // 等会查询到
           o.setId(null);
           o.setName(null);
           o.setAbbreviation(null);
           o.setCreateTime(null);
       });
       organizationOtherMapper.insert(dbOrganizationOther);
       // 测试 id 不匹配
       organizationOtherMapper.insert(cloneIgnoreId(dbOrganizationOther, o -> o.setId(null)));
       // 测试 name 不匹配
       organizationOtherMapper.insert(cloneIgnoreId(dbOrganizationOther, o -> o.setName(null)));
       // 测试 abbreviation 不匹配
       organizationOtherMapper.insert(cloneIgnoreId(dbOrganizationOther, o -> o.setAbbreviation(null)));
       // 测试 createTime 不匹配
       organizationOtherMapper.insert(cloneIgnoreId(dbOrganizationOther, o -> o.setCreateTime(null)));
       // 准备参数
       OrganizationOtherPageReqVO reqVO = new OrganizationOtherPageReqVO();
       reqVO.setId(null);
       reqVO.setName(null);
       reqVO.setAbbreviation(null);
       reqVO.setCreateTime(buildBetweenTime(2023, 2, 1, 2023, 2, 28));

       // 调用
       PageResult<OrganizationOtherDO> pageResult = organizationOtherService.getOrganizationOtherPage(reqVO);
       // 断言
       assertEquals(1, pageResult.getTotal());
       assertEquals(1, pageResult.getList().size());
       assertPojoEquals(dbOrganizationOther, pageResult.getList().get(0));
    }

    @Test
    @Disabled  // TODO 请修改 null 为需要的值，然后删除 @Disabled 注解
    public void testGetOrganizationOtherList() {
       // mock 数据
       OrganizationOtherDO dbOrganizationOther = randomPojo(OrganizationOtherDO.class, o -> { // 等会查询到
           o.setId(null);
           o.setName(null);
           o.setAbbreviation(null);
           o.setCreateTime(null);
       });
       organizationOtherMapper.insert(dbOrganizationOther);
       // 测试 id 不匹配
       organizationOtherMapper.insert(cloneIgnoreId(dbOrganizationOther, o -> o.setId(null)));
       // 测试 name 不匹配
       organizationOtherMapper.insert(cloneIgnoreId(dbOrganizationOther, o -> o.setName(null)));
       // 测试 abbreviation 不匹配
       organizationOtherMapper.insert(cloneIgnoreId(dbOrganizationOther, o -> o.setAbbreviation(null)));
       // 测试 createTime 不匹配
       organizationOtherMapper.insert(cloneIgnoreId(dbOrganizationOther, o -> o.setCreateTime(null)));
       // 准备参数
       OrganizationOtherExportReqVO reqVO = new OrganizationOtherExportReqVO();
       reqVO.setId(null);
       reqVO.setName(null);
       reqVO.setAbbreviation(null);
       reqVO.setCreateTime(buildBetweenTime(2023, 2, 1, 2023, 2, 28));

       // 调用
       List<OrganizationOtherDO> list = organizationOtherService.getOrganizationOtherList(reqVO);
       // 断言
       assertEquals(1, list.size());
       assertPojoEquals(dbOrganizationOther, list.get(0));
    }

}
