package com.practice.module.system.service.signin;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

import javax.annotation.Resource;

import com.practice.framework.test.core.ut.BaseDbUnitTest;

import com.practice.module.system.controller.admin.signin.vo.*;
import com.practice.module.system.dal.dataobject.signin.SignInDO;
import com.practice.module.system.dal.mysql.signin.SignInMapper;
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
* {@link SignInServiceImpl} 的单元测试类
*
* @author 觅践
*/
@Import(SignInServiceImpl.class)
public class SignInServiceImplTest extends BaseDbUnitTest {

    @Resource
    private SignInServiceImpl signInService;

    @Resource
    private SignInMapper signInMapper;

    @Test
    public void testCreateSignIn_success() {
        // 准备参数
        SignInCreateReqVO reqVO = randomPojo(SignInCreateReqVO.class);

        // 调用
        SignInDO signInId = signInService.createSignIn(reqVO);
        // 断言
        assertNotNull(signInId);
        // 校验记录的属性是否正确
        SignInDO signIn = signInMapper.selectById(signInId);
        assertPojoEquals(reqVO, signIn);
    }

    @Test
    public void testUpdateSignIn_success() {
        // mock 数据
        SignInDO dbSignIn = randomPojo(SignInDO.class);
        signInMapper.insert(dbSignIn);// @Sql: 先插入出一条存在的数据
        // 准备参数
        SignInUpdateReqVO reqVO = randomPojo(SignInUpdateReqVO.class, o -> {
            o.setId(dbSignIn.getId()); // 设置更新的 ID
        });

        // 调用
        signInService.updateSignOut(reqVO);
        // 校验是否更新正确
        SignInDO signIn = signInMapper.selectById(reqVO.getId()); // 获取最新的
        assertPojoEquals(reqVO, signIn);
    }

    @Test
    public void testUpdateSignIn_notExists() {
        // 准备参数
        SignInUpdateReqVO reqVO = randomPojo(SignInUpdateReqVO.class);

        // 调用, 并断言异常
        assertServiceException(() -> signInService.updateSignOut(reqVO), SIGN_IN_NOT_EXISTS);
    }

    @Test
    public void testDeleteSignIn_success() {
        // mock 数据
        SignInDO dbSignIn = randomPojo(SignInDO.class);
        signInMapper.insert(dbSignIn);// @Sql: 先插入出一条存在的数据
        // 准备参数
        Long id = dbSignIn.getId();

        // 调用
        signInService.deleteSignIn(id);
       // 校验数据不存在了
       assertNull(signInMapper.selectById(id));
    }

    @Test
    public void testDeleteSignIn_notExists() {
        // 准备参数
        Long id = randomLongId();

        // 调用, 并断言异常
        assertServiceException(() -> signInService.deleteSignIn(id), SIGN_IN_NOT_EXISTS);
    }

    @Test
    @Disabled  // TODO 请修改 null 为需要的值，然后删除 @Disabled 注解
    public void testGetSignInPage() {
       // mock 数据
       SignInDO dbSignIn = randomPojo(SignInDO.class, o -> { // 等会查询到
           o.setUserId(null);
           o.setPracticeId(null);
           o.setStatus(null);
           o.setResult(null);
           o.setSignInLocation(null);
           o.setSignOutLocation(null);
           o.setCreateTime(null);
       });
       signInMapper.insert(dbSignIn);
       // 测试 userId 不匹配
       signInMapper.insert(cloneIgnoreId(dbSignIn, o -> o.setUserId(null)));
       // 测试 practiceId 不匹配
       signInMapper.insert(cloneIgnoreId(dbSignIn, o -> o.setPracticeId(null)));
       // 测试 status 不匹配
       signInMapper.insert(cloneIgnoreId(dbSignIn, o -> o.setStatus(null)));
       // 测试 result 不匹配
       signInMapper.insert(cloneIgnoreId(dbSignIn, o -> o.setResult(null)));
       // 测试 signInLocation 不匹配
       signInMapper.insert(cloneIgnoreId(dbSignIn, o -> o.setSignInLocation(null)));
       // 测试 signOutLocation 不匹配
       signInMapper.insert(cloneIgnoreId(dbSignIn, o -> o.setSignOutLocation(null)));
       // 测试 createTime 不匹配
       signInMapper.insert(cloneIgnoreId(dbSignIn, o -> o.setCreateTime(null)));
       // 准备参数
       SignInPageReqVO reqVO = new SignInPageReqVO();
       reqVO.setUserId(null);
       reqVO.setPracticeId(null);
       reqVO.setStatus(null);
       reqVO.setResult(null);
       reqVO.setSignInLocation(null);
       reqVO.setSignOutLocation(null);
       reqVO.setCreateTime(buildBetweenTime(2023, 2, 1, 2023, 2, 28));

       // 调用
       PageResult<SignInDO> pageResult = signInService.getSignInPage(reqVO);
       // 断言
       assertEquals(1, pageResult.getTotal());
       assertEquals(1, pageResult.getList().size());
       assertPojoEquals(dbSignIn, pageResult.getList().get(0));
    }

    @Test
    @Disabled  // TODO 请修改 null 为需要的值，然后删除 @Disabled 注解
    public void testGetSignInList() {
       // mock 数据
       SignInDO dbSignIn = randomPojo(SignInDO.class, o -> { // 等会查询到
           o.setUserId(null);
           o.setPracticeId(null);
           o.setStatus(null);
           o.setResult(null);
           o.setSignInLocation(null);
           o.setSignOutLocation(null);
           o.setCreateTime(null);
       });
       signInMapper.insert(dbSignIn);
       // 测试 userId 不匹配
       signInMapper.insert(cloneIgnoreId(dbSignIn, o -> o.setUserId(null)));
       // 测试 practiceId 不匹配
       signInMapper.insert(cloneIgnoreId(dbSignIn, o -> o.setPracticeId(null)));
       // 测试 status 不匹配
       signInMapper.insert(cloneIgnoreId(dbSignIn, o -> o.setStatus(null)));
       // 测试 result 不匹配
       signInMapper.insert(cloneIgnoreId(dbSignIn, o -> o.setResult(null)));
       // 测试 signInLocation 不匹配
       signInMapper.insert(cloneIgnoreId(dbSignIn, o -> o.setSignInLocation(null)));
       // 测试 signOutLocation 不匹配
       signInMapper.insert(cloneIgnoreId(dbSignIn, o -> o.setSignOutLocation(null)));
       // 测试 createTime 不匹配
       signInMapper.insert(cloneIgnoreId(dbSignIn, o -> o.setCreateTime(null)));
       // 准备参数
       SignInExportReqVO reqVO = new SignInExportReqVO();
       reqVO.setUserId(null);
       reqVO.setPracticeId(null);
       reqVO.setStatus(null);
       reqVO.setResult(null);
       reqVO.setSignInLocation(null);
       reqVO.setSignOutLocation(null);
       reqVO.setCreateTime(buildBetweenTime(2023, 2, 1, 2023, 2, 28));

       // 调用
       List<SignInDO> list = signInService.getSignInList(reqVO);
       // 断言
       assertEquals(1, list.size());
       assertPojoEquals(dbSignIn, list.get(0));
    }

}
