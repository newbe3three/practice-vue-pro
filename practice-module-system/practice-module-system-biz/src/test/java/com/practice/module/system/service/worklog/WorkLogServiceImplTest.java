package com.practice.module.system.service.worklog;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

import javax.annotation.Resource;

import com.practice.framework.test.core.ut.BaseDbUnitTest;

import com.practice.module.system.controller.admin.worklog.vo.*;
import com.practice.module.system.dal.dataobject.worklog.WorkLogDO;
import com.practice.module.system.dal.mysql.worklog.WorkLogMapper;
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
* {@link WorkLogServiceImpl} 的单元测试类
*
* @author 觅践
*/
@Import(WorkLogServiceImpl.class)
public class WorkLogServiceImplTest extends BaseDbUnitTest {

    @Resource
    private WorkLogServiceImpl workLogService;

    @Resource
    private WorkLogMapper workLogMapper;

    @Test
    public void testCreateWorkLog_success() {
        // 准备参数
        WorkLogCreateReqVO reqVO = randomPojo(WorkLogCreateReqVO.class);

        // 调用
        Long workLogId = workLogService.createWorkLog(reqVO);
        // 断言
        assertNotNull(workLogId);
        // 校验记录的属性是否正确
        WorkLogDO workLog = workLogMapper.selectById(workLogId);
        assertPojoEquals(reqVO, workLog);
    }

    @Test
    public void testUpdateWorkLog_success() {
        // mock 数据
        WorkLogDO dbWorkLog = randomPojo(WorkLogDO.class);
        workLogMapper.insert(dbWorkLog);// @Sql: 先插入出一条存在的数据
        // 准备参数
        WorkLogUpdateReqVO reqVO = randomPojo(WorkLogUpdateReqVO.class, o -> {
            o.setId(dbWorkLog.getId()); // 设置更新的 ID
        });

        // 调用
        workLogService.updateWorkLog(reqVO);
        // 校验是否更新正确
        WorkLogDO workLog = workLogMapper.selectById(reqVO.getId()); // 获取最新的
        assertPojoEquals(reqVO, workLog);
    }

    @Test
    public void testUpdateWorkLog_notExists() {
        // 准备参数
        WorkLogUpdateReqVO reqVO = randomPojo(WorkLogUpdateReqVO.class);

        // 调用, 并断言异常
        assertServiceException(() -> workLogService.updateWorkLog(reqVO), WORK_LOG_NOT_EXISTS);
    }

    @Test
    public void testDeleteWorkLog_success() {
        // mock 数据
        WorkLogDO dbWorkLog = randomPojo(WorkLogDO.class);
        workLogMapper.insert(dbWorkLog);// @Sql: 先插入出一条存在的数据
        // 准备参数
        Long id = dbWorkLog.getId();

        // 调用
        workLogService.deleteWorkLog(id);
       // 校验数据不存在了
       assertNull(workLogMapper.selectById(id));
    }

    @Test
    public void testDeleteWorkLog_notExists() {
        // 准备参数
        Long id = randomLongId();

        // 调用, 并断言异常
        assertServiceException(() -> workLogService.deleteWorkLog(id), WORK_LOG_NOT_EXISTS);
    }

    @Test
    @Disabled  // TODO 请修改 null 为需要的值，然后删除 @Disabled 注解
    public void testGetWorkLogPage() {
       // mock 数据
       WorkLogDO dbWorkLog = randomPojo(WorkLogDO.class, o -> { // 等会查询到
           o.setUserId(null);
           o.setPracticeId(null);
           o.setCreateTime(null);
       });
       workLogMapper.insert(dbWorkLog);
       // 测试 userId 不匹配
       workLogMapper.insert(cloneIgnoreId(dbWorkLog, o -> o.setUserId(null)));
       // 测试 practiceId 不匹配
       workLogMapper.insert(cloneIgnoreId(dbWorkLog, o -> o.setPracticeId(null)));
       // 测试 createTime 不匹配
       workLogMapper.insert(cloneIgnoreId(dbWorkLog, o -> o.setCreateTime(null)));
       // 准备参数
       WorkLogPageReqVO reqVO = new WorkLogPageReqVO();
       reqVO.setUserId(null);
       reqVO.setPracticeId(null);
       reqVO.setCreateTime(buildBetweenTime(2023, 2, 1, 2023, 2, 28));

       // 调用
       PageResult<WorkLogDO> pageResult = workLogService.getWorkLogPage(reqVO);
       // 断言
       assertEquals(1, pageResult.getTotal());
       assertEquals(1, pageResult.getList().size());
       assertPojoEquals(dbWorkLog, pageResult.getList().get(0));
    }

    @Test
    @Disabled  // TODO 请修改 null 为需要的值，然后删除 @Disabled 注解
    public void testGetWorkLogList() {
       // mock 数据
       WorkLogDO dbWorkLog = randomPojo(WorkLogDO.class, o -> { // 等会查询到
           o.setUserId(null);
           o.setPracticeId(null);
           o.setCreateTime(null);
       });
       workLogMapper.insert(dbWorkLog);
       // 测试 userId 不匹配
       workLogMapper.insert(cloneIgnoreId(dbWorkLog, o -> o.setUserId(null)));
       // 测试 practiceId 不匹配
       workLogMapper.insert(cloneIgnoreId(dbWorkLog, o -> o.setPracticeId(null)));
       // 测试 createTime 不匹配
       workLogMapper.insert(cloneIgnoreId(dbWorkLog, o -> o.setCreateTime(null)));
       // 准备参数
       WorkLogExportReqVO reqVO = new WorkLogExportReqVO();
       reqVO.setUserId(null);
       reqVO.setPracticeId(null);
       reqVO.setCreateTime(buildBetweenTime(2023, 2, 1, 2023, 2, 28));

       // 调用
       List<WorkLogDO> list = workLogService.getWorkLogList(reqVO);
       // 断言
       assertEquals(1, list.size());
       assertPojoEquals(dbWorkLog, list.get(0));
    }

}
