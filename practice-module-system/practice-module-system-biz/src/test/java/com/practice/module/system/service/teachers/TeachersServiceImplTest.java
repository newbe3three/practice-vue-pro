package com.practice.module.system.service.teachers;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

import javax.annotation.Resource;

import com.practice.framework.test.core.ut.BaseDbUnitTest;

import com.practice.module.system.controller.admin.teachers.vo.*;
import com.practice.module.system.dal.dataobject.teachers.TeachersDO;
import com.practice.module.system.dal.mysql.teachers.TeachersMapper;
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
* {@link TeachersServiceImpl} 的单元测试类
*
* @author n3
*/
@Import(TeachersServiceImpl.class)
public class TeachersServiceImplTest extends BaseDbUnitTest {

    @Resource
    private TeachersServiceImpl teachersService;

    @Resource
    private TeachersMapper teachersMapper;

    @Test
    public void testCreateTeachers_success() {
        // 准备参数
        TeachersCreateReqVO reqVO = randomPojo(TeachersCreateReqVO.class);

        // 调用
        Long teachersId = teachersService.createTeachers(reqVO);
        // 断言
        assertNotNull(teachersId);
        // 校验记录的属性是否正确
        TeachersDO teachers = teachersMapper.selectById(teachersId);
        assertPojoEquals(reqVO, teachers);
    }

    @Test
    public void testUpdateTeachers_success() {
        // mock 数据
        TeachersDO dbTeachers = randomPojo(TeachersDO.class);
        teachersMapper.insert(dbTeachers);// @Sql: 先插入出一条存在的数据
        // 准备参数
        TeachersUpdateReqVO reqVO = randomPojo(TeachersUpdateReqVO.class, o -> {
            o.setId(dbTeachers.getId()); // 设置更新的 ID
        });

        // 调用
        teachersService.updateTeachers(reqVO);
        // 校验是否更新正确
        TeachersDO teachers = teachersMapper.selectById(reqVO.getId()); // 获取最新的
        assertPojoEquals(reqVO, teachers);
    }

    @Test
    public void testUpdateTeachers_notExists() {
        // 准备参数
        TeachersUpdateReqVO reqVO = randomPojo(TeachersUpdateReqVO.class);

        // 调用, 并断言异常
        assertServiceException(() -> teachersService.updateTeachers(reqVO), TEACHERS_NOT_EXISTS);
    }

    @Test
    public void testDeleteTeachers_success() {
        // mock 数据
        TeachersDO dbTeachers = randomPojo(TeachersDO.class);
        teachersMapper.insert(dbTeachers);// @Sql: 先插入出一条存在的数据
        // 准备参数
        Long id = dbTeachers.getId();

        // 调用
        teachersService.deleteTeachers(id);
       // 校验数据不存在了
       assertNull(teachersMapper.selectById(id));
    }

    @Test
    public void testDeleteTeachers_notExists() {
        // 准备参数
        Long id = randomLongId();

        // 调用, 并断言异常
        assertServiceException(() -> teachersService.deleteTeachers(id), TEACHERS_NOT_EXISTS);
    }

    @Test
    @Disabled  // TODO 请修改 null 为需要的值，然后删除 @Disabled 注解
    public void testGetTeachersPage() {
       // mock 数据
       TeachersDO dbTeachers = randomPojo(TeachersDO.class, o -> { // 等会查询到
           o.setUserId(null);
           o.setName(null);
           o.setDeptId(null);
           o.setMajor(null);
           o.setMobile(null);
           o.setCardId(null);
           o.setCreateTime(null);
       });
       teachersMapper.insert(dbTeachers);
       // 测试 userId 不匹配
       teachersMapper.insert(cloneIgnoreId(dbTeachers, o -> o.setUserId(null)));
       // 测试 name 不匹配
       teachersMapper.insert(cloneIgnoreId(dbTeachers, o -> o.setName(null)));
       // 测试 deptId 不匹配
       teachersMapper.insert(cloneIgnoreId(dbTeachers, o -> o.setDeptId(null)));
       // 测试 major 不匹配
       teachersMapper.insert(cloneIgnoreId(dbTeachers, o -> o.setMajor(null)));
       // 测试 mobile 不匹配
       teachersMapper.insert(cloneIgnoreId(dbTeachers, o -> o.setMobile(null)));
       // 测试 cardId 不匹配
       teachersMapper.insert(cloneIgnoreId(dbTeachers, o -> o.setCardId(null)));
       // 测试 createTime 不匹配
       teachersMapper.insert(cloneIgnoreId(dbTeachers, o -> o.setCreateTime(null)));
       // 准备参数
       TeachersPageReqVO reqVO = new TeachersPageReqVO();
       reqVO.setUserId(null);
       reqVO.setName(null);
       reqVO.setDeptId(null);
       reqVO.setMajor(null);
       reqVO.setMobile(null);
       reqVO.setCardId(null);
       reqVO.setCreateTime(buildBetweenTime(2023, 2, 1, 2023, 2, 28));

       // 调用
       PageResult<TeachersDO> pageResult = teachersService.getTeachersPage(reqVO);
       // 断言
       assertEquals(1, pageResult.getTotal());
       assertEquals(1, pageResult.getList().size());
       assertPojoEquals(dbTeachers, pageResult.getList().get(0));
    }

    @Test
    @Disabled  // TODO 请修改 null 为需要的值，然后删除 @Disabled 注解
    public void testGetTeachersList() {
       // mock 数据
       TeachersDO dbTeachers = randomPojo(TeachersDO.class, o -> { // 等会查询到
           o.setUserId(null);
           o.setName(null);
           o.setDeptId(null);
           o.setMajor(null);
           o.setMobile(null);
           o.setCardId(null);
           o.setCreateTime(null);
       });
       teachersMapper.insert(dbTeachers);
       // 测试 userId 不匹配
       teachersMapper.insert(cloneIgnoreId(dbTeachers, o -> o.setUserId(null)));
       // 测试 name 不匹配
       teachersMapper.insert(cloneIgnoreId(dbTeachers, o -> o.setName(null)));
       // 测试 deptId 不匹配
       teachersMapper.insert(cloneIgnoreId(dbTeachers, o -> o.setDeptId(null)));
       // 测试 major 不匹配
       teachersMapper.insert(cloneIgnoreId(dbTeachers, o -> o.setMajor(null)));
       // 测试 mobile 不匹配
       teachersMapper.insert(cloneIgnoreId(dbTeachers, o -> o.setMobile(null)));
       // 测试 cardId 不匹配
       teachersMapper.insert(cloneIgnoreId(dbTeachers, o -> o.setCardId(null)));
       // 测试 createTime 不匹配
       teachersMapper.insert(cloneIgnoreId(dbTeachers, o -> o.setCreateTime(null)));
       // 准备参数
       TeachersExportReqVO reqVO = new TeachersExportReqVO();
       reqVO.setUserId(null);
       reqVO.setName(null);
       reqVO.setDeptId(null);
       reqVO.setMajor(null);
       reqVO.setMobile(null);
       reqVO.setCardId(null);
       reqVO.setCreateTime(buildBetweenTime(2023, 2, 1, 2023, 2, 28));

       // 调用
       List<TeachersDO> list = teachersService.getTeachersList(reqVO);
       // 断言
       assertEquals(1, list.size());
       assertPojoEquals(dbTeachers, list.get(0));
    }

}
