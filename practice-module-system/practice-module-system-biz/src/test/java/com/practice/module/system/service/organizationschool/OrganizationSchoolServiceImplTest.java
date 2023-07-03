package com.practice.module.system.service.organizationschool;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

import javax.annotation.Resource;

import com.practice.framework.test.core.ut.BaseDbUnitTest;

import com.practice.module.system.controller.admin.organizationschool.vo.*;
import com.practice.module.system.dal.dataobject.organizationschool.OrganizationSchoolDO;
import com.practice.module.system.dal.mysql.organizationschool.OrganizationSchoolMapper;
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
* {@link OrganizationSchoolServiceImpl} 的单元测试类
*
* @author wx
*/
@Import(OrganizationSchoolServiceImpl.class)
public class OrganizationSchoolServiceImplTest extends BaseDbUnitTest {

    @Resource
    private OrganizationSchoolServiceImpl organizationSchoolService;

    @Resource
    private OrganizationSchoolMapper organizationSchoolMapper;

    @Test
    public void testCreateOrganizationSchool_success() {
        // 准备参数
        OrganizationSchoolCreateReqVO reqVO = randomPojo(OrganizationSchoolCreateReqVO.class);

        // 调用
        Long organizationSchoolId = organizationSchoolService.createOrganizationSchool(reqVO);
        // 断言
        assertNotNull(organizationSchoolId);
        // 校验记录的属性是否正确
        OrganizationSchoolDO organizationSchool = organizationSchoolMapper.selectById(organizationSchoolId);
        assertPojoEquals(reqVO, organizationSchool);
    }

    @Test
    public void testUpdateOrganizationSchool_success() {
        // mock 数据
        OrganizationSchoolDO dbOrganizationSchool = randomPojo(OrganizationSchoolDO.class);
        organizationSchoolMapper.insert(dbOrganizationSchool);// @Sql: 先插入出一条存在的数据
        // 准备参数
        OrganizationSchoolUpdateReqVO reqVO = randomPojo(OrganizationSchoolUpdateReqVO.class, o -> {
            o.setId(dbOrganizationSchool.getId()); // 设置更新的 ID
        });

        // 调用
        organizationSchoolService.updateOrganizationSchool(reqVO);
        // 校验是否更新正确
        OrganizationSchoolDO organizationSchool = organizationSchoolMapper.selectById(reqVO.getId()); // 获取最新的
        assertPojoEquals(reqVO, organizationSchool);
    }

    @Test
    public void testUpdateOrganizationSchool_notExists() {
        // 准备参数
        OrganizationSchoolUpdateReqVO reqVO = randomPojo(OrganizationSchoolUpdateReqVO.class);

        // 调用, 并断言异常
        assertServiceException(() -> organizationSchoolService.updateOrganizationSchool(reqVO), ORGANIZATION_SCHOOL_NOT_EXISTS);
    }

    @Test
    public void testDeleteOrganizationSchool_success() {
        // mock 数据
        OrganizationSchoolDO dbOrganizationSchool = randomPojo(OrganizationSchoolDO.class);
        organizationSchoolMapper.insert(dbOrganizationSchool);// @Sql: 先插入出一条存在的数据
        // 准备参数
        Long id = dbOrganizationSchool.getId();

        // 调用
        organizationSchoolService.deleteOrganizationSchool(id);
       // 校验数据不存在了
       assertNull(organizationSchoolMapper.selectById(id));
    }

    @Test
    public void testDeleteOrganizationSchool_notExists() {
        // 准备参数
        Long id = randomLongId();

        // 调用, 并断言异常
        assertServiceException(() -> organizationSchoolService.deleteOrganizationSchool(id), ORGANIZATION_SCHOOL_NOT_EXISTS);
    }

    @Test
    @Disabled  // TODO 请修改 null 为需要的值，然后删除 @Disabled 注解
    public void testGetOrganizationSchoolPage() {
       // mock 数据
       OrganizationSchoolDO dbOrganizationSchool = randomPojo(OrganizationSchoolDO.class, o -> { // 等会查询到
           o.setId(null);
           o.setName(null);
           o.setAbbreviation(null);
           o.setCreateTime(null);
       });
       organizationSchoolMapper.insert(dbOrganizationSchool);
       // 测试 id 不匹配
       organizationSchoolMapper.insert(cloneIgnoreId(dbOrganizationSchool, o -> o.setId(null)));
       // 测试 name 不匹配
       organizationSchoolMapper.insert(cloneIgnoreId(dbOrganizationSchool, o -> o.setName(null)));
       // 测试 abbreviation 不匹配
       organizationSchoolMapper.insert(cloneIgnoreId(dbOrganizationSchool, o -> o.setAbbreviation(null)));
       // 测试 createTime 不匹配
       organizationSchoolMapper.insert(cloneIgnoreId(dbOrganizationSchool, o -> o.setCreateTime(null)));
       // 准备参数
       OrganizationSchoolPageReqVO reqVO = new OrganizationSchoolPageReqVO();
       reqVO.setId(null);
       reqVO.setName(null);
       reqVO.setAbbreviation(null);
       reqVO.setCreateTime(buildBetweenTime(2023, 2, 1, 2023, 2, 28));

       // 调用
       PageResult<OrganizationSchoolDO> pageResult = organizationSchoolService.getOrganizationSchoolPage(reqVO);
       // 断言
       assertEquals(1, pageResult.getTotal());
       assertEquals(1, pageResult.getList().size());
       assertPojoEquals(dbOrganizationSchool, pageResult.getList().get(0));
    }

    @Test
    @Disabled  // TODO 请修改 null 为需要的值，然后删除 @Disabled 注解
    public void testGetOrganizationSchoolList() {
       // mock 数据
       OrganizationSchoolDO dbOrganizationSchool = randomPojo(OrganizationSchoolDO.class, o -> { // 等会查询到
           o.setId(null);
           o.setName(null);
           o.setAbbreviation(null);
           o.setCreateTime(null);
       });
       organizationSchoolMapper.insert(dbOrganizationSchool);
       // 测试 id 不匹配
       organizationSchoolMapper.insert(cloneIgnoreId(dbOrganizationSchool, o -> o.setId(null)));
       // 测试 name 不匹配
       organizationSchoolMapper.insert(cloneIgnoreId(dbOrganizationSchool, o -> o.setName(null)));
       // 测试 abbreviation 不匹配
       organizationSchoolMapper.insert(cloneIgnoreId(dbOrganizationSchool, o -> o.setAbbreviation(null)));
       // 测试 createTime 不匹配
       organizationSchoolMapper.insert(cloneIgnoreId(dbOrganizationSchool, o -> o.setCreateTime(null)));
       // 准备参数
       OrganizationSchoolExportReqVO reqVO = new OrganizationSchoolExportReqVO();
       reqVO.setId(null);
       reqVO.setName(null);
       reqVO.setAbbreviation(null);
       reqVO.setCreateTime(buildBetweenTime(2023, 2, 1, 2023, 2, 28));

       // 调用
       List<OrganizationSchoolDO> list = organizationSchoolService.getOrganizationSchoolList(reqVO);
       // 断言
       assertEquals(1, list.size());
       assertPojoEquals(dbOrganizationSchool, list.get(0));
    }

}
