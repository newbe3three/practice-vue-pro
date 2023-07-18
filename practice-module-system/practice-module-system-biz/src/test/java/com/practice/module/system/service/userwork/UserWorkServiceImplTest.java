package com.practice.module.system.service.userwork;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

import javax.annotation.Resource;

import com.practice.framework.test.core.ut.BaseDbUnitTest;

import com.practice.module.system.controller.admin.userwork.vo.*;
import com.practice.module.system.dal.dataobject.userwork.UserWorkDO;
import com.practice.module.system.dal.mysql.userwork.UserWorkMapper;
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
* {@link UserWorkServiceImpl} 的单元测试类
*
* @author n3
*/
@Import(UserWorkServiceImpl.class)
public class UserWorkServiceImplTest extends BaseDbUnitTest {

    @Resource
    private UserWorkServiceImpl userWorkService;

    @Resource
    private UserWorkMapper userWorkMapper;

    @Test
    public void testCreateUserWork_success() {
        // 准备参数
        UserWorkCreateReqVO reqVO = randomPojo(UserWorkCreateReqVO.class);

        // 调用
        Long userWorkId = userWorkService.createUserWork(reqVO);
        // 断言
        assertNotNull(userWorkId);
        // 校验记录的属性是否正确
        UserWorkDO userWork = userWorkMapper.selectById(userWorkId);
        assertPojoEquals(reqVO, userWork);
    }

    @Test
    public void testUpdateUserWork_success() {
        // mock 数据
        UserWorkDO dbUserWork = randomPojo(UserWorkDO.class);
        userWorkMapper.insert(dbUserWork);// @Sql: 先插入出一条存在的数据
        // 准备参数
        UserWorkUpdateReqVO reqVO = randomPojo(UserWorkUpdateReqVO.class, o -> {
            o.setId(dbUserWork.getId()); // 设置更新的 ID
        });

        // 调用
        userWorkService.updateUserWork(reqVO);
        // 校验是否更新正确
        UserWorkDO userWork = userWorkMapper.selectById(reqVO.getId()); // 获取最新的
        assertPojoEquals(reqVO, userWork);
    }

    @Test
    public void testUpdateUserWork_notExists() {
        // 准备参数
        UserWorkUpdateReqVO reqVO = randomPojo(UserWorkUpdateReqVO.class);

        // 调用, 并断言异常
        assertServiceException(() -> userWorkService.updateUserWork(reqVO), USER_WORK_NOT_EXISTS);
    }

    @Test
//    public void testDeleteUserWork_success() {
//        // mock 数据
//        UserWorkDO dbUserWork = randomPojo(UserWorkDO.class);
//        userWorkMapper.insert(dbUserWork);// @Sql: 先插入出一条存在的数据
//        // 准备参数
//        Long id = dbUserWork.getId();
//
//        // 调用
//        userWorkService.deleteUserWork(id);
//       // 校验数据不存在了
//       assertNull(userWorkMapper.selectById(id));
//    }

//    @Test
//    public void testDeleteUserWork_notExists() {
//        // 准备参数
//        Long id = randomLongId();
//
//        // 调用, 并断言异常
//        assertServiceException(() -> userWorkService.deleteUserWork(id), USER_WORK_NOT_EXISTS);
//    }

    @Test
    @Disabled  // TODO 请修改 null 为需要的值，然后删除 @Disabled 注解
    public void testGetUserWorkPage() {
       // mock 数据
       UserWorkDO dbUserWork = randomPojo(UserWorkDO.class, o -> { // 等会查询到
           o.setUserId(null);
           o.setName(null);
           o.setType(null);
           o.setCompanyName(null);
           o.setDescription(null);
           o.setCreateTime(null);
           o.setStartTime(null);
           o.setEndTime(null);
       });
       userWorkMapper.insert(dbUserWork);
       // 测试 userId 不匹配
       userWorkMapper.insert(cloneIgnoreId(dbUserWork, o -> o.setUserId(null)));
       // 测试 name 不匹配
       userWorkMapper.insert(cloneIgnoreId(dbUserWork, o -> o.setName(null)));
       // 测试 type 不匹配
       userWorkMapper.insert(cloneIgnoreId(dbUserWork, o -> o.setType(null)));
       // 测试 companyName 不匹配
       userWorkMapper.insert(cloneIgnoreId(dbUserWork, o -> o.setCompanyName(null)));
       // 测试 description 不匹配
       userWorkMapper.insert(cloneIgnoreId(dbUserWork, o -> o.setDescription(null)));
       // 测试 createTime 不匹配
       userWorkMapper.insert(cloneIgnoreId(dbUserWork, o -> o.setCreateTime(null)));
       // 测试 startTime 不匹配
       userWorkMapper.insert(cloneIgnoreId(dbUserWork, o -> o.setStartTime(null)));
       // 测试 endTime 不匹配
       userWorkMapper.insert(cloneIgnoreId(dbUserWork, o -> o.setEndTime(null)));
       // 准备参数
       UserWorkPageReqVO reqVO = new UserWorkPageReqVO();
       reqVO.setUserId(null);
       reqVO.setName(null);
       reqVO.setType(null);
       reqVO.setCompanyName(null);
       reqVO.setDescription(null);
       reqVO.setCreateTime(buildBetweenTime(2023, 2, 1, 2023, 2, 28));
       reqVO.setStartTime(buildBetweenTime(2023, 2, 1, 2023, 2, 28));
       reqVO.setEndTime(buildBetweenTime(2023, 2, 1, 2023, 2, 28));

       // 调用
       PageResult<UserWorkDO> pageResult = userWorkService.getUserWorkPage(reqVO);
       // 断言
       assertEquals(1, pageResult.getTotal());
       assertEquals(1, pageResult.getList().size());
       assertPojoEquals(dbUserWork, pageResult.getList().get(0));
    }

    @Test
    @Disabled  // TODO 请修改 null 为需要的值，然后删除 @Disabled 注解
    public void testGetUserWorkList() {
       // mock 数据
       UserWorkDO dbUserWork = randomPojo(UserWorkDO.class, o -> { // 等会查询到
           o.setUserId(null);
           o.setName(null);
           o.setType(null);
           o.setCompanyName(null);
           o.setDescription(null);
           o.setCreateTime(null);
           o.setStartTime(null);
           o.setEndTime(null);
       });
       userWorkMapper.insert(dbUserWork);
       // 测试 userId 不匹配
       userWorkMapper.insert(cloneIgnoreId(dbUserWork, o -> o.setUserId(null)));
       // 测试 name 不匹配
       userWorkMapper.insert(cloneIgnoreId(dbUserWork, o -> o.setName(null)));
       // 测试 type 不匹配
       userWorkMapper.insert(cloneIgnoreId(dbUserWork, o -> o.setType(null)));
       // 测试 companyName 不匹配
       userWorkMapper.insert(cloneIgnoreId(dbUserWork, o -> o.setCompanyName(null)));
       // 测试 description 不匹配
       userWorkMapper.insert(cloneIgnoreId(dbUserWork, o -> o.setDescription(null)));
       // 测试 createTime 不匹配
       userWorkMapper.insert(cloneIgnoreId(dbUserWork, o -> o.setCreateTime(null)));
       // 测试 startTime 不匹配
       userWorkMapper.insert(cloneIgnoreId(dbUserWork, o -> o.setStartTime(null)));
       // 测试 endTime 不匹配
       userWorkMapper.insert(cloneIgnoreId(dbUserWork, o -> o.setEndTime(null)));
       // 准备参数
       UserWorkExportReqVO reqVO = new UserWorkExportReqVO();
       reqVO.setUserId(null);
       reqVO.setName(null);
       reqVO.setType(null);
       reqVO.setCompanyName(null);
       reqVO.setDescription(null);
       reqVO.setCreateTime(buildBetweenTime(2023, 2, 1, 2023, 2, 28));
       reqVO.setStartTime(buildBetweenTime(2023, 2, 1, 2023, 2, 28));
       reqVO.setEndTime(buildBetweenTime(2023, 2, 1, 2023, 2, 28));

       // 调用
       List<UserWorkDO> list = userWorkService.getUserWorkList(reqVO);
       // 断言
       assertEquals(1, list.size());
       assertPojoEquals(dbUserWork, list.get(0));
    }

}
