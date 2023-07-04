package com.practice.module.system.service.practiceschool;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

import javax.annotation.Resource;

import com.practice.framework.test.core.ut.BaseDbUnitTest;

import com.practice.module.system.controller.admin.practiceschool.vo.*;
import com.practice.module.system.dal.dataobject.practiceschool.PracticeSchoolDO;
import com.practice.module.system.dal.mysql.practiceschool.PracticeSchoolMapper;
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
* {@link PracticeSchoolServiceImpl} 的单元测试类
*
* @author n3
*/
@Import(PracticeSchoolServiceImpl.class)
public class PracticeSchoolServiceImplTest extends BaseDbUnitTest {

    @Resource
    private PracticeSchoolServiceImpl practiceSchoolService;

    @Resource
    private PracticeSchoolMapper practiceSchoolMapper;

    @Test
    public void testCreatePracticeSchool_success() {
        // 准备参数
        PracticeSchoolCreateReqVO reqVO = randomPojo(PracticeSchoolCreateReqVO.class);

        // 调用
        Long practiceSchoolId = practiceSchoolService.createPracticeSchool(reqVO);
        // 断言
        assertNotNull(practiceSchoolId);
        // 校验记录的属性是否正确
        PracticeSchoolDO practiceSchool = practiceSchoolMapper.selectById(practiceSchoolId);
        assertPojoEquals(reqVO, practiceSchool);
    }

    @Test
    public void testUpdatePracticeSchool_success() {
        // mock 数据
        PracticeSchoolDO dbPracticeSchool = randomPojo(PracticeSchoolDO.class);
        practiceSchoolMapper.insert(dbPracticeSchool);// @Sql: 先插入出一条存在的数据
        // 准备参数
        PracticeSchoolUpdateReqVO reqVO = randomPojo(PracticeSchoolUpdateReqVO.class, o -> {
            o.setId(dbPracticeSchool.getId()); // 设置更新的 ID
        });

        // 调用
        practiceSchoolService.updatePracticeSchool(reqVO);
        // 校验是否更新正确
        PracticeSchoolDO practiceSchool = practiceSchoolMapper.selectById(reqVO.getId()); // 获取最新的
        assertPojoEquals(reqVO, practiceSchool);
    }

    @Test
    public void testUpdatePracticeSchool_notExists() {
        // 准备参数
        PracticeSchoolUpdateReqVO reqVO = randomPojo(PracticeSchoolUpdateReqVO.class);

        // 调用, 并断言异常
        assertServiceException(() -> practiceSchoolService.updatePracticeSchool(reqVO), PRACTICE_SCHOOL_NOT_EXISTS);
    }

    @Test
    public void testDeletePracticeSchool_success() {
        // mock 数据
        PracticeSchoolDO dbPracticeSchool = randomPojo(PracticeSchoolDO.class);
        practiceSchoolMapper.insert(dbPracticeSchool);// @Sql: 先插入出一条存在的数据
        // 准备参数
        Long id = dbPracticeSchool.getId();

        // 调用
        practiceSchoolService.deletePracticeSchool(id);
       // 校验数据不存在了
       assertNull(practiceSchoolMapper.selectById(id));
    }

    @Test
    public void testDeletePracticeSchool_notExists() {
        // 准备参数
        Long id = randomLongId();

        // 调用, 并断言异常
        assertServiceException(() -> practiceSchoolService.deletePracticeSchool(id), PRACTICE_SCHOOL_NOT_EXISTS);
    }

    @Test
    @Disabled  // TODO 请修改 null 为需要的值，然后删除 @Disabled 注解
    public void testGetPracticeSchoolPage() {
       // mock 数据
       PracticeSchoolDO dbPracticeSchool = randomPojo(PracticeSchoolDO.class, o -> { // 等会查询到
           o.setSchoolId(null);
           o.setPracticeId(null);
           o.setStatus(null);
           o.setCreateTime(null);
       });
       practiceSchoolMapper.insert(dbPracticeSchool);
       // 测试 schoolId 不匹配
       practiceSchoolMapper.insert(cloneIgnoreId(dbPracticeSchool, o -> o.setSchoolId(null)));
       // 测试 practiceId 不匹配
       practiceSchoolMapper.insert(cloneIgnoreId(dbPracticeSchool, o -> o.setPracticeId(null)));
       // 测试 status 不匹配
       practiceSchoolMapper.insert(cloneIgnoreId(dbPracticeSchool, o -> o.setStatus(null)));
       // 测试 createTime 不匹配
       practiceSchoolMapper.insert(cloneIgnoreId(dbPracticeSchool, o -> o.setCreateTime(null)));
       // 准备参数
       PracticeSchoolPageReqVO reqVO = new PracticeSchoolPageReqVO();
       reqVO.setSchoolId(null);
       reqVO.setPracticeId(null);
       reqVO.setStatus(null);
       reqVO.setCreateTime(buildBetweenTime(2023, 2, 1, 2023, 2, 28));

       // 调用
       PageResult<PracticeSchoolDO> pageResult = practiceSchoolService.getPracticeSchoolPage(reqVO);
       // 断言
       assertEquals(1, pageResult.getTotal());
       assertEquals(1, pageResult.getList().size());
       assertPojoEquals(dbPracticeSchool, pageResult.getList().get(0));
    }

    @Test
    @Disabled  // TODO 请修改 null 为需要的值，然后删除 @Disabled 注解
    public void testGetPracticeSchoolList() {
       // mock 数据
       PracticeSchoolDO dbPracticeSchool = randomPojo(PracticeSchoolDO.class, o -> { // 等会查询到
           o.setSchoolId(null);
           o.setPracticeId(null);
           o.setStatus(null);
           o.setCreateTime(null);
       });
       practiceSchoolMapper.insert(dbPracticeSchool);
       // 测试 schoolId 不匹配
       practiceSchoolMapper.insert(cloneIgnoreId(dbPracticeSchool, o -> o.setSchoolId(null)));
       // 测试 practiceId 不匹配
       practiceSchoolMapper.insert(cloneIgnoreId(dbPracticeSchool, o -> o.setPracticeId(null)));
       // 测试 status 不匹配
       practiceSchoolMapper.insert(cloneIgnoreId(dbPracticeSchool, o -> o.setStatus(null)));
       // 测试 createTime 不匹配
       practiceSchoolMapper.insert(cloneIgnoreId(dbPracticeSchool, o -> o.setCreateTime(null)));
       // 准备参数
       PracticeSchoolExportReqVO reqVO = new PracticeSchoolExportReqVO();
       reqVO.setSchoolId(null);
       reqVO.setPracticeId(null);
       reqVO.setStatus(null);
       reqVO.setCreateTime(buildBetweenTime(2023, 2, 1, 2023, 2, 28));

       // 调用
       List<PracticeSchoolDO> list = practiceSchoolService.getPracticeSchoolList(reqVO);
       // 断言
       assertEquals(1, list.size());
       assertPojoEquals(dbPracticeSchool, list.get(0));
    }

}
