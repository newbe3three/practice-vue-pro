package com.practice.module.system.service.practice;

import com.practice.module.system.controller.admin.practice.vo.practice.PracticeCreateReqVO;
import com.practice.module.system.controller.admin.practice.vo.practice.PracticeExportReqVO;
import com.practice.module.system.controller.admin.practice.vo.practice.PracticePageReqVO;
import com.practice.module.system.controller.admin.practice.vo.practice.PracticeUpdateReqVO;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import javax.annotation.Resource;

import com.practice.framework.test.core.ut.BaseDbUnitTest;

import com.practice.module.system.dal.dataobject.practice.PracticeDO;
import com.practice.module.system.dal.mysql.practice.PracticeMapper;
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
* {@link PracticeServiceImpl} 的单元测试类
*
* @author n3
*/
@Import(PracticeServiceImpl.class)
public class PracticeServiceImplTest extends BaseDbUnitTest {

    @Resource
    private PracticeServiceImpl practiceService;

    @Resource
    private PracticeMapper practiceMapper;

    @Test
    public void testCreatePractice_success() {
        // 准备参数
        PracticeCreateReqVO reqVO = randomPojo(PracticeCreateReqVO.class);

        // 调用
        Long practiceId = practiceService.createPractice(reqVO);
        // 断言
        assertNotNull(practiceId);
        // 校验记录的属性是否正确
        PracticeDO practice = practiceMapper.selectById(practiceId);
        assertPojoEquals(reqVO, practice);
    }

    @Test
    public void testUpdatePractice_success() {
        // mock 数据
        PracticeDO dbPractice = randomPojo(PracticeDO.class);
        practiceMapper.insert(dbPractice);// @Sql: 先插入出一条存在的数据
        // 准备参数
        PracticeUpdateReqVO reqVO = randomPojo(PracticeUpdateReqVO.class, o -> {
            o.setId(dbPractice.getId()); // 设置更新的 ID
        });

        // 调用
        practiceService.updatePracticeApply(reqVO);
        // 校验是否更新正确
        PracticeDO practice = practiceMapper.selectById(reqVO.getId()); // 获取最新的
        assertPojoEquals(reqVO, practice);
    }

    @Test
    public void testUpdatePractice_notExists() {
        // 准备参数
        PracticeUpdateReqVO reqVO = randomPojo(PracticeUpdateReqVO.class);

        // 调用, 并断言异常
        assertServiceException(() -> practiceService.updatePracticeApply(reqVO), PRACTICE_NOT_EXISTS);
    }

    @Test
    public void testDeletePractice_success() {
        // mock 数据
        PracticeDO dbPractice = randomPojo(PracticeDO.class);
        practiceMapper.insert(dbPractice);// @Sql: 先插入出一条存在的数据
        // 准备参数
        Long id = dbPractice.getId();

        // 调用
        practiceService.deletePractice(id);
       // 校验数据不存在了
       assertNull(practiceMapper.selectById(id));
    }

    @Test
    public void testDeletePractice_notExists() {
        // 准备参数
        Long id = randomLongId();

        // 调用, 并断言异常
        assertServiceException(() -> practiceService.deletePractice(id), PRACTICE_NOT_EXISTS);
    }

    @Test
    @Disabled  // TODO 请修改 null 为需要的值，然后删除 @Disabled 注解
    public void testGetPracticePage() {
       // mock 数据
       PracticeDO dbPractice = randomPojo(PracticeDO.class, o -> { // 等会查询到
           o.setName(null);
           o.setContent(null);
           o.setStartTime(null);
           o.setEndTime(null);
           o.setNumberPeople(null);
           o.setStatus(null);

           o.setCreateTime(null);
       });
       practiceMapper.insert(dbPractice);
       // 测试 name 不匹配
       practiceMapper.insert(cloneIgnoreId(dbPractice, o -> o.setName(null)));
       // 测试 content 不匹配
       practiceMapper.insert(cloneIgnoreId(dbPractice, o -> o.setContent(null)));
       // 测试 startTime 不匹配
       practiceMapper.insert(cloneIgnoreId(dbPractice, o -> o.setStartTime(null)));
       // 测试 endTime 不匹配
       practiceMapper.insert(cloneIgnoreId(dbPractice, o -> o.setEndTime(null)));
       // 测试 numberPeople 不匹配
       practiceMapper.insert(cloneIgnoreId(dbPractice, o -> o.setNumberPeople(null)));
       // 测试 status 不匹配
       practiceMapper.insert(cloneIgnoreId(dbPractice, o -> o.setStatus(null)));
       // 测试 suggestion 不匹配
       // 测试 createTime 不匹配
       practiceMapper.insert(cloneIgnoreId(dbPractice, o -> o.setCreateTime(null)));
       // 准备参数
       PracticePageReqVO reqVO = new PracticePageReqVO();
       reqVO.setName(null);
       reqVO.setContent(null);
       reqVO.setStartTime(buildBetweenTime(2023, 2, 1, 2023, 2, 28));
       reqVO.setEndTime(buildBetweenTime(2023, 2, 1, 2023, 2, 28));
       reqVO.setNumberPeople(null);
       reqVO.setStatus(null);
       reqVO.setCreateTime(buildBetweenTime(2023, 2, 1, 2023, 2, 28));

       // 调用
       PageResult<PracticeDO> pageResult = practiceService.getPracticePage(reqVO);
       // 断言
       assertEquals(1, pageResult.getTotal());
       assertEquals(1, pageResult.getList().size());
       assertPojoEquals(dbPractice, pageResult.getList().get(0));
    }

    @Test
    @Disabled  // TODO 请修改 null 为需要的值，然后删除 @Disabled 注解
    public void testGetPracticeList() {
       // mock 数据
       PracticeDO dbPractice = randomPojo(PracticeDO.class, o -> { // 等会查询到
           o.setName(null);
           o.setContent(null);
           o.setStartTime(null);
           o.setEndTime(null);
           o.setNumberPeople(null);
           o.setStatus(null);
           o.setCreateTime(null);
       });
       practiceMapper.insert(dbPractice);
       // 测试 name 不匹配
       practiceMapper.insert(cloneIgnoreId(dbPractice, o -> o.setName(null)));
       // 测试 content 不匹配
       practiceMapper.insert(cloneIgnoreId(dbPractice, o -> o.setContent(null)));
       // 测试 startTime 不匹配
       practiceMapper.insert(cloneIgnoreId(dbPractice, o -> o.setStartTime(null)));
       // 测试 endTime 不匹配
       practiceMapper.insert(cloneIgnoreId(dbPractice, o -> o.setEndTime(null)));
       // 测试 numberPeople 不匹配
       practiceMapper.insert(cloneIgnoreId(dbPractice, o -> o.setNumberPeople(null)));
       // 测试 status 不匹配
       practiceMapper.insert(cloneIgnoreId(dbPractice, o -> o.setStatus(null)));
       // 测试 suggestion 不匹配
       // 测试 createTime 不匹配
       practiceMapper.insert(cloneIgnoreId(dbPractice, o -> o.setCreateTime(null)));
       // 准备参数
       PracticeExportReqVO reqVO = new PracticeExportReqVO();
       reqVO.setName(null);
       reqVO.setContent(null);
       reqVO.setStartTime(buildBetweenTime(2023, 2, 1, 2023, 2, 28));
       reqVO.setEndTime(buildBetweenTime(2023, 2, 1, 2023, 2, 28));
       reqVO.setNumberPeople(null);
       reqVO.setStatus(null);

       reqVO.setCreateTime(buildBetweenTime(2023, 2, 1, 2023, 2, 28));

       // 调用
       List<PracticeDO> list = practiceService.getPracticeList(reqVO);
       // 断言
       assertEquals(1, list.size());
       assertPojoEquals(dbPractice, list.get(0));
    }

}
