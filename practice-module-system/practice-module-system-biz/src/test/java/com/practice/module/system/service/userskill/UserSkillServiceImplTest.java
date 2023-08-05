package com.practice.module.system.service.userskill;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

import javax.annotation.Resource;

import com.practice.framework.test.core.ut.BaseDbUnitTest;

import com.practice.module.system.controller.admin.userskill.vo.*;
import com.practice.module.system.dal.dataobject.userskill.UserSkillDO;
import com.practice.module.system.dal.mysql.userskill.UserSkillMapper;
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
* {@link UserSkillServiceImpl} 的单元测试类
*
* @author n3
*/
@Import(UserSkillServiceImpl.class)
public class UserSkillServiceImplTest extends BaseDbUnitTest {

    @Resource
    private UserSkillServiceImpl userSkillService;

    @Resource
    private UserSkillMapper userSkillMapper;

    @Test
    public void testCreateUserSkill_success() {
        // 准备参数
        UserSkillCreateReqVO reqVO = randomPojo(UserSkillCreateReqVO.class);

        // 调用
        Long userSkillId = userSkillService.createUserSkill(reqVO);
        // 断言
        assertNotNull(userSkillId);
        // 校验记录的属性是否正确
        UserSkillDO userSkill = userSkillMapper.selectById(userSkillId);
        assertPojoEquals(reqVO, userSkill);
    }

    @Test
    public void testUpdateUserSkill_success() {
        // mock 数据
        UserSkillDO dbUserSkill = randomPojo(UserSkillDO.class);
        userSkillMapper.insert(dbUserSkill);// @Sql: 先插入出一条存在的数据
        // 准备参数
        UserSkillUpdateReqVO reqVO = randomPojo(UserSkillUpdateReqVO.class, o -> {
            o.setId(dbUserSkill.getId()); // 设置更新的 ID
        });

        // 调用
        userSkillService.updateUserSkill(reqVO);
        // 校验是否更新正确
        UserSkillDO userSkill = userSkillMapper.selectById(reqVO.getId()); // 获取最新的
        assertPojoEquals(reqVO, userSkill);
    }

    @Test
    public void testUpdateUserSkill_notExists() {
        // 准备参数
        UserSkillUpdateReqVO reqVO = randomPojo(UserSkillUpdateReqVO.class);

        // 调用, 并断言异常
        assertServiceException(() -> userSkillService.updateUserSkill(reqVO), USER_SKILL_NOT_EXISTS);
    }

//    @Test
//    public void testDeleteUserSkill_success() {
//        // mock 数据
//        UserSkillDO dbUserSkill = randomPojo(UserSkillDO.class);
//        userSkillMapper.insert(dbUserSkill);// @Sql: 先插入出一条存在的数据
//        // 准备参数
//        Long id = dbUserSkill.getId();
//
//        // 调用
//        userSkillService.deleteUserSkill(id);
//       // 校验数据不存在了
//       assertNull(userSkillMapper.selectById(id));
//    }

//    @Test
//    public void testDeleteUserSkill_notExists() {
//        // 准备参数
//        Long id = randomLongId();
//
//        // 调用, 并断言异常
//        assertServiceException(() -> userSkillService.deleteUserSkill(id), USER_SKILL_NOT_EXISTS);
//    }

    @Test
    @Disabled  // TODO 请修改 null 为需要的值，然后删除 @Disabled 注解
    public void testGetUserSkillPage() {
       // mock 数据
       UserSkillDO dbUserSkill = randomPojo(UserSkillDO.class, o -> { // 等会查询到
           o.setUserId(null);
           o.setSkill(null);
           o.setLevel(null);
           o.setCreateTime(null);
       });
       userSkillMapper.insert(dbUserSkill);
       // 测试 userId 不匹配
       userSkillMapper.insert(cloneIgnoreId(dbUserSkill, o -> o.setUserId(null)));
       // 测试 skill 不匹配
       userSkillMapper.insert(cloneIgnoreId(dbUserSkill, o -> o.setSkill(null)));
       // 测试 level 不匹配
       userSkillMapper.insert(cloneIgnoreId(dbUserSkill, o -> o.setLevel(null)));
       // 测试 createTime 不匹配
       userSkillMapper.insert(cloneIgnoreId(dbUserSkill, o -> o.setCreateTime(null)));
       // 准备参数
       UserSkillPageReqVO reqVO = new UserSkillPageReqVO();
       reqVO.setUserId(null);
       reqVO.setSkill(null);
       reqVO.setLevel(null);
       reqVO.setCreateTime(buildBetweenTime(2023, 2, 1, 2023, 2, 28));

       // 调用
       PageResult<UserSkillDO> pageResult = userSkillService.getUserSkillPage(reqVO);
       // 断言
       assertEquals(1, pageResult.getTotal());
       assertEquals(1, pageResult.getList().size());
       assertPojoEquals(dbUserSkill, pageResult.getList().get(0));
    }

    @Test
    @Disabled  // TODO 请修改 null 为需要的值，然后删除 @Disabled 注解
    public void testGetUserSkillList() {
       // mock 数据
       UserSkillDO dbUserSkill = randomPojo(UserSkillDO.class, o -> { // 等会查询到
           o.setUserId(null);
           o.setSkill(null);
           o.setLevel(null);
           o.setCreateTime(null);
       });
       userSkillMapper.insert(dbUserSkill);
       // 测试 userId 不匹配
       userSkillMapper.insert(cloneIgnoreId(dbUserSkill, o -> o.setUserId(null)));
       // 测试 skill 不匹配
       userSkillMapper.insert(cloneIgnoreId(dbUserSkill, o -> o.setSkill(null)));
       // 测试 level 不匹配
       userSkillMapper.insert(cloneIgnoreId(dbUserSkill, o -> o.setLevel(null)));
       // 测试 createTime 不匹配
       userSkillMapper.insert(cloneIgnoreId(dbUserSkill, o -> o.setCreateTime(null)));
       // 准备参数
       UserSkillExportReqVO reqVO = new UserSkillExportReqVO();
       reqVO.setUserId(null);
       reqVO.setSkill(null);
       reqVO.setLevel(null);
       reqVO.setCreateTime(buildBetweenTime(2023, 2, 1, 2023, 2, 28));

       // 调用
       List<UserSkillDO> list = userSkillService.getUserSkillList(reqVO);
       // 断言
       assertEquals(1, list.size());
       assertPojoEquals(dbUserSkill, list.get(0));
    }

}
