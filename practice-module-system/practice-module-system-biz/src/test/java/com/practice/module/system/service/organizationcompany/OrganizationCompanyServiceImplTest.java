package com.practice.module.system.service.organizationcompany;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

import javax.annotation.Resource;

import com.practice.framework.test.core.ut.BaseDbUnitTest;

import com.practice.module.system.controller.admin.organizationcompany.vo.*;
import com.practice.module.system.dal.dataobject.organizationcompany.OrganizationCompanyDO;
import com.practice.module.system.dal.mysql.organizationcompany.OrganizationCompanyMapper;
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
* {@link OrganizationCompanyServiceImpl} 的单元测试类
*
* @author wx
*/
@Import(OrganizationCompanyServiceImpl.class)
public class OrganizationCompanyServiceImplTest extends BaseDbUnitTest {

    @Resource
    private OrganizationCompanyServiceImpl organizationCompanyService;

    @Resource
    private OrganizationCompanyMapper organizationCompanyMapper;

    @Test
    public void testCreateOrganizationCompany_success() {
        // 准备参数
        OrganizationCompanyCreateReqVO reqVO = randomPojo(OrganizationCompanyCreateReqVO.class);

        // 调用
        Long organizationCompanyId = organizationCompanyService.createOrganizationCompany(reqVO);
        // 断言
        assertNotNull(organizationCompanyId);
        // 校验记录的属性是否正确
        OrganizationCompanyDO organizationCompany = organizationCompanyMapper.selectById(organizationCompanyId);
        assertPojoEquals(reqVO, organizationCompany);
    }

    @Test
    public void testUpdateOrganizationCompany_success() {
        // mock 数据
        OrganizationCompanyDO dbOrganizationCompany = randomPojo(OrganizationCompanyDO.class);
        organizationCompanyMapper.insert(dbOrganizationCompany);// @Sql: 先插入出一条存在的数据
        // 准备参数
        OrganizationCompanyUpdateReqVO reqVO = randomPojo(OrganizationCompanyUpdateReqVO.class, o -> {
            o.setId(dbOrganizationCompany.getId()); // 设置更新的 ID
        });

        // 调用
        organizationCompanyService.updateOrganizationCompany(reqVO);
        // 校验是否更新正确
        OrganizationCompanyDO organizationCompany = organizationCompanyMapper.selectById(reqVO.getId()); // 获取最新的
        assertPojoEquals(reqVO, organizationCompany);
    }

    @Test
    public void testUpdateOrganizationCompany_notExists() {
        // 准备参数
        OrganizationCompanyUpdateReqVO reqVO = randomPojo(OrganizationCompanyUpdateReqVO.class);

        // 调用, 并断言异常
        assertServiceException(() -> organizationCompanyService.updateOrganizationCompany(reqVO), ORGANIZATION_COMPANY_NOT_EXISTS);
    }

    @Test
    public void testDeleteOrganizationCompany_success() {
        // mock 数据
        OrganizationCompanyDO dbOrganizationCompany = randomPojo(OrganizationCompanyDO.class);
        organizationCompanyMapper.insert(dbOrganizationCompany);// @Sql: 先插入出一条存在的数据
        // 准备参数
        Long id = dbOrganizationCompany.getId();

        // 调用
        organizationCompanyService.deleteOrganizationCompany(id);
       // 校验数据不存在了
       assertNull(organizationCompanyMapper.selectById(id));
    }

    @Test
    public void testDeleteOrganizationCompany_notExists() {
        // 准备参数
        Long id = randomLongId();

        // 调用, 并断言异常
        assertServiceException(() -> organizationCompanyService.deleteOrganizationCompany(id), ORGANIZATION_COMPANY_NOT_EXISTS);
    }

    @Test
    @Disabled  // TODO 请修改 null 为需要的值，然后删除 @Disabled 注解
    public void testGetOrganizationCompanyPage() {
       // mock 数据
       OrganizationCompanyDO dbOrganizationCompany = randomPojo(OrganizationCompanyDO.class, o -> { // 等会查询到
           o.setId(null);
           o.setName(null);
           o.setAbbreviation(null);
           o.setCreateTime(null);
       });
       organizationCompanyMapper.insert(dbOrganizationCompany);
       // 测试 id 不匹配
       organizationCompanyMapper.insert(cloneIgnoreId(dbOrganizationCompany, o -> o.setId(null)));
       // 测试 name 不匹配
       organizationCompanyMapper.insert(cloneIgnoreId(dbOrganizationCompany, o -> o.setName(null)));
       // 测试 abbreviation 不匹配
       organizationCompanyMapper.insert(cloneIgnoreId(dbOrganizationCompany, o -> o.setAbbreviation(null)));
       // 测试 createTime 不匹配
       organizationCompanyMapper.insert(cloneIgnoreId(dbOrganizationCompany, o -> o.setCreateTime(null)));
       // 准备参数
       OrganizationCompanyPageReqVO reqVO = new OrganizationCompanyPageReqVO();
       reqVO.setId(null);
       reqVO.setName(null);
       reqVO.setAbbreviation(null);
       reqVO.setCreateTime(buildBetweenTime(2023, 2, 1, 2023, 2, 28));

       // 调用
       PageResult<OrganizationCompanyDO> pageResult = organizationCompanyService.getOrganizationCompanyPage(reqVO);
       // 断言
       assertEquals(1, pageResult.getTotal());
       assertEquals(1, pageResult.getList().size());
       assertPojoEquals(dbOrganizationCompany, pageResult.getList().get(0));
    }

    @Test
    @Disabled  // TODO 请修改 null 为需要的值，然后删除 @Disabled 注解
    public void testGetOrganizationCompanyList() {
       // mock 数据
       OrganizationCompanyDO dbOrganizationCompany = randomPojo(OrganizationCompanyDO.class, o -> { // 等会查询到
           o.setId(null);
           o.setName(null);
           o.setAbbreviation(null);
           o.setCreateTime(null);
       });
       organizationCompanyMapper.insert(dbOrganizationCompany);
       // 测试 id 不匹配
       organizationCompanyMapper.insert(cloneIgnoreId(dbOrganizationCompany, o -> o.setId(null)));
       // 测试 name 不匹配
       organizationCompanyMapper.insert(cloneIgnoreId(dbOrganizationCompany, o -> o.setName(null)));
       // 测试 abbreviation 不匹配
       organizationCompanyMapper.insert(cloneIgnoreId(dbOrganizationCompany, o -> o.setAbbreviation(null)));
       // 测试 createTime 不匹配
       organizationCompanyMapper.insert(cloneIgnoreId(dbOrganizationCompany, o -> o.setCreateTime(null)));
       // 准备参数
       OrganizationCompanyExportReqVO reqVO = new OrganizationCompanyExportReqVO();
       reqVO.setId(null);
       reqVO.setName(null);
       reqVO.setAbbreviation(null);
       reqVO.setCreateTime(buildBetweenTime(2023, 2, 1, 2023, 2, 28));

       // 调用
       List<OrganizationCompanyDO> list = organizationCompanyService.getOrganizationCompanyList(reqVO);
       // 断言
       assertEquals(1, list.size());
       assertPojoEquals(dbOrganizationCompany, list.get(0));
    }

}
