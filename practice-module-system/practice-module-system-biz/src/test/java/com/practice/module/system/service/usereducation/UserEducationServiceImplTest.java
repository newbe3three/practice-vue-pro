package com.practice.module.system.service.usereducation;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

import javax.annotation.Resource;

import com.practice.framework.test.core.ut.BaseDbUnitTest;

import com.practice.module.system.controller.admin.usereducation.vo.*;
import com.practice.module.system.dal.dataobject.usereducation.UserEducationDO;
import com.practice.module.system.dal.mysql.usereducation.UserEducationMapper;
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
* {@link UserEducationServiceImpl} 的单元测试类
*
* @author n3
*/
@Import(UserEducationServiceImpl.class)
public class UserEducationServiceImplTest extends BaseDbUnitTest {

    @Resource
    private UserEducationServiceImpl userEducationService;

    @Resource
    private UserEducationMapper userEducationMapper;

    @Test
    public void testCreateUserEducation_success() {
        // 准备参数
        UserEducationCreateReqVO reqVO = randomPojo(UserEducationCreateReqVO.class);

        // 调用
        Long userEducationId = userEducationService.createUserEducation(reqVO);
        // 断言
        assertNotNull(userEducationId);
        // 校验记录的属性是否正确
        UserEducationDO userEducation = userEducationMapper.selectById(userEducationId);
        assertPojoEquals(reqVO, userEducation);
    }

    @Test
    public void testUpdateUserEducation_success() {
        // mock 数据
        UserEducationDO dbUserEducation = randomPojo(UserEducationDO.class);
        userEducationMapper.insert(dbUserEducation);// @Sql: 先插入出一条存在的数据
        // 准备参数
        UserEducationUpdateReqVO reqVO = randomPojo(UserEducationUpdateReqVO.class, o -> {
            o.setId(dbUserEducation.getId()); // 设置更新的 ID
        });

        // 调用
        userEducationService.updateUserEducation(reqVO);
        // 校验是否更新正确
        UserEducationDO userEducation = userEducationMapper.selectById(reqVO.getId()); // 获取最新的
        assertPojoEquals(reqVO, userEducation);
    }

    @Test
    public void testUpdateUserEducation_notExists() {
        // 准备参数
        UserEducationUpdateReqVO reqVO = randomPojo(UserEducationUpdateReqVO.class);

        // 调用, 并断言异常
        assertServiceException(() -> userEducationService.updateUserEducation(reqVO), USER_EDUCATION_NOT_EXISTS);
    }

//    @Test
//    public void testDeleteUserEducation_success() {
//        // mock 数据
//        UserEducationDO dbUserEducation = randomPojo(UserEducationDO.class);
//        userEducationMapper.insert(dbUserEducation);// @Sql: 先插入出一条存在的数据
//        // 准备参数
//        Long id = dbUserEducation.getId();
//
//        // 调用
//        userEducationService.deleteUserEducation(id);
//       // 校验数据不存在了
//       assertNull(userEducationMapper.selectById(id));
//    }
//
//    @Test
//    public void testDeleteUserEducation_notExists() {
//        // 准备参数
//        Long id = randomLongId();
//
//        // 调用, 并断言异常
//        assertServiceException(() -> userEducationService.deleteUserEducation(id), USER_EDUCATION_NOT_EXISTS);
//    }

    @Test
    @Disabled  // TODO 请修改 null 为需要的值，然后删除 @Disabled 注解
    public void testGetUserEducationPage() {
       // mock 数据
       UserEducationDO dbUserEducation = randomPojo(UserEducationDO.class, o -> { // 等会查询到
           o.setUserId(null);
           o.setMajor(null);
           o.setTeacher(null);
           o.setSchoolName(null);
           o.setDescription(null);
           o.setStartTime(null);
           o.setEndTime(null);
           o.setCreateTime(null);
       });
       userEducationMapper.insert(dbUserEducation);
       // 测试 userId 不匹配
       userEducationMapper.insert(cloneIgnoreId(dbUserEducation, o -> o.setUserId(null)));
       // 测试 major 不匹配
       userEducationMapper.insert(cloneIgnoreId(dbUserEducation, o -> o.setMajor(null)));
       // 测试 teacher 不匹配
       userEducationMapper.insert(cloneIgnoreId(dbUserEducation, o -> o.setTeacher(null)));
       // 测试 schoolName 不匹配
       userEducationMapper.insert(cloneIgnoreId(dbUserEducation, o -> o.setSchoolName(null)));
       // 测试 description 不匹配
       userEducationMapper.insert(cloneIgnoreId(dbUserEducation, o -> o.setDescription(null)));
       // 测试 startTime 不匹配
       userEducationMapper.insert(cloneIgnoreId(dbUserEducation, o -> o.setStartTime(null)));
       // 测试 endTime 不匹配
       userEducationMapper.insert(cloneIgnoreId(dbUserEducation, o -> o.setEndTime(null)));
       // 测试 createTime 不匹配
       userEducationMapper.insert(cloneIgnoreId(dbUserEducation, o -> o.setCreateTime(null)));
       // 准备参数
       UserEducationPageReqVO reqVO = new UserEducationPageReqVO();
       reqVO.setUserId(null);
       reqVO.setMajor(null);
       reqVO.setTeacher(null);
       reqVO.setSchoolName(null);
       reqVO.setDescription(null);
       reqVO.setStartTime(buildBetweenTime(2023, 2, 1, 2023, 2, 28));
       reqVO.setEndTime(buildBetweenTime(2023, 2, 1, 2023, 2, 28));
       reqVO.setCreateTime(buildBetweenTime(2023, 2, 1, 2023, 2, 28));

       // 调用
       PageResult<UserEducationDO> pageResult = userEducationService.getUserEducationPage(reqVO);
       // 断言
       assertEquals(1, pageResult.getTotal());
       assertEquals(1, pageResult.getList().size());
       assertPojoEquals(dbUserEducation, pageResult.getList().get(0));
    }

    @Test
    @Disabled  // TODO 请修改 null 为需要的值，然后删除 @Disabled 注解
    public void testGetUserEducationList() {
       // mock 数据
       UserEducationDO dbUserEducation = randomPojo(UserEducationDO.class, o -> { // 等会查询到
           o.setUserId(null);
           o.setMajor(null);
           o.setTeacher(null);
           o.setSchoolName(null);
           o.setDescription(null);
           o.setStartTime(null);
           o.setEndTime(null);
           o.setCreateTime(null);
       });
       userEducationMapper.insert(dbUserEducation);
       // 测试 userId 不匹配
       userEducationMapper.insert(cloneIgnoreId(dbUserEducation, o -> o.setUserId(null)));
       // 测试 major 不匹配
       userEducationMapper.insert(cloneIgnoreId(dbUserEducation, o -> o.setMajor(null)));
       // 测试 teacher 不匹配
       userEducationMapper.insert(cloneIgnoreId(dbUserEducation, o -> o.setTeacher(null)));
       // 测试 schoolName 不匹配
       userEducationMapper.insert(cloneIgnoreId(dbUserEducation, o -> o.setSchoolName(null)));
       // 测试 description 不匹配
       userEducationMapper.insert(cloneIgnoreId(dbUserEducation, o -> o.setDescription(null)));
       // 测试 startTime 不匹配
       userEducationMapper.insert(cloneIgnoreId(dbUserEducation, o -> o.setStartTime(null)));
       // 测试 endTime 不匹配
       userEducationMapper.insert(cloneIgnoreId(dbUserEducation, o -> o.setEndTime(null)));
       // 测试 createTime 不匹配
       userEducationMapper.insert(cloneIgnoreId(dbUserEducation, o -> o.setCreateTime(null)));
       // 准备参数
       UserEducationExportReqVO reqVO = new UserEducationExportReqVO();
       reqVO.setUserId(null);
       reqVO.setMajor(null);
       reqVO.setTeacher(null);
       reqVO.setSchoolName(null);
       reqVO.setDescription(null);
       reqVO.setStartTime(buildBetweenTime(2023, 2, 1, 2023, 2, 28));
       reqVO.setEndTime(buildBetweenTime(2023, 2, 1, 2023, 2, 28));
       reqVO.setCreateTime(buildBetweenTime(2023, 2, 1, 2023, 2, 28));

       // 调用
       List<UserEducationDO> list = userEducationService.getUserEducationList(reqVO);
       // 断言
       assertEquals(1, list.size());
       assertPojoEquals(dbUserEducation, list.get(0));
    }

}
