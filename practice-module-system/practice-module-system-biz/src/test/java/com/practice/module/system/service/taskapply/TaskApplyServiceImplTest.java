package com.practice.module.system.service.taskapply;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

import javax.annotation.Resource;

import com.practice.framework.test.core.ut.BaseDbUnitTest;

import com.practice.module.system.controller.admin.taskapply.vo.*;
import com.practice.module.system.dal.dataobject.taskapply.TaskApplyDO;
import com.practice.module.system.dal.mysql.taskapply.TaskApplyMapper;
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
* {@link TaskApplyServiceImpl} 的单元测试类
*
* @author n3
*/
@Import(TaskApplyServiceImpl.class)
public class TaskApplyServiceImplTest extends BaseDbUnitTest {

    @Resource
    private TaskApplyServiceImpl taskApplyService;

    @Resource
    private TaskApplyMapper taskApplyMapper;

    @Test
    public void testCreateTaskApply_success() {
        // 准备参数
        TaskApplyCreateReqVO reqVO = randomPojo(TaskApplyCreateReqVO.class);

        // 调用
        Long taskApplyId = taskApplyService.createTaskApply(reqVO);
        // 断言
        assertNotNull(taskApplyId);
        // 校验记录的属性是否正确
        TaskApplyDO taskApply = taskApplyMapper.selectById(taskApplyId);
        assertPojoEquals(reqVO, taskApply);
    }

    @Test
    public void testUpdateTaskApply_success() {
        // mock 数据
        TaskApplyDO dbTaskApply = randomPojo(TaskApplyDO.class);
        taskApplyMapper.insert(dbTaskApply);// @Sql: 先插入出一条存在的数据
        // 准备参数
        TaskApplyUpdateReqVO reqVO = randomPojo(TaskApplyUpdateReqVO.class, o -> {
            o.setId(dbTaskApply.getId()); // 设置更新的 ID
        });

        // 调用
        taskApplyService.updateTaskApply(reqVO);
        // 校验是否更新正确
        TaskApplyDO taskApply = taskApplyMapper.selectById(reqVO.getId()); // 获取最新的
        assertPojoEquals(reqVO, taskApply);
    }

    @Test
    public void testUpdateTaskApply_notExists() {
        // 准备参数
        TaskApplyUpdateReqVO reqVO = randomPojo(TaskApplyUpdateReqVO.class);

        // 调用, 并断言异常
        assertServiceException(() -> taskApplyService.updateTaskApply(reqVO), TASK_APPLY_NOT_EXISTS);
    }

    @Test
    public void testDeleteTaskApply_success() {
        // mock 数据
        TaskApplyDO dbTaskApply = randomPojo(TaskApplyDO.class);
        taskApplyMapper.insert(dbTaskApply);// @Sql: 先插入出一条存在的数据
        // 准备参数
        Long id = dbTaskApply.getId();

        // 调用
        taskApplyService.deleteTaskApply(id);
       // 校验数据不存在了
       assertNull(taskApplyMapper.selectById(id));
    }

    @Test
    public void testDeleteTaskApply_notExists() {
        // 准备参数
        Long id = randomLongId();

        // 调用, 并断言异常
        assertServiceException(() -> taskApplyService.deleteTaskApply(id), TASK_APPLY_NOT_EXISTS);
    }

    @Test
    @Disabled  // TODO 请修改 null 为需要的值，然后删除 @Disabled 注解
    public void testGetTaskApplyPage() {
       // mock 数据
       TaskApplyDO dbTaskApply = randomPojo(TaskApplyDO.class, o -> { // 等会查询到
           o.setUserId(null);
           o.setDeptId(null);
           o.setCreator(null);
           o.setTaskId(null);
           o.setMobile(null);
           o.setStatus(null);
           o.setCreateTime(null);
       });
       taskApplyMapper.insert(dbTaskApply);
       // 测试 name 不匹配
       taskApplyMapper.insert(cloneIgnoreId(dbTaskApply, o -> o.setUserId(null)));
       // 测试 deptName 不匹配
       taskApplyMapper.insert(cloneIgnoreId(dbTaskApply, o -> o.setDeptId(null)));
       // 测试 creator 不匹配
       taskApplyMapper.insert(cloneIgnoreId(dbTaskApply, o -> o.setCreator(null)));
       // 测试 taskId 不匹配
       taskApplyMapper.insert(cloneIgnoreId(dbTaskApply, o -> o.setTaskId(null)));
       // 测试 mobile 不匹配
       taskApplyMapper.insert(cloneIgnoreId(dbTaskApply, o -> o.setMobile(null)));
       // 测试 status 不匹配
       taskApplyMapper.insert(cloneIgnoreId(dbTaskApply, o -> o.setStatus(null)));
       // 测试 createTime 不匹配
       taskApplyMapper.insert(cloneIgnoreId(dbTaskApply, o -> o.setCreateTime(null)));
       // 准备参数
       TaskApplyPageReqVO reqVO = new TaskApplyPageReqVO();
       reqVO.setUserId(null);
       reqVO.setDeptId(null);
       reqVO.setCreator(null);
       reqVO.setTaskId(null);
       reqVO.setMobile(null);
       reqVO.setStatus(null);
       reqVO.setCreateTime(buildBetweenTime(2023, 2, 1, 2023, 2, 28));

       // 调用
       PageResult<TaskApplyDO> pageResult = taskApplyService.getTaskApplyPage(reqVO);
       // 断言
       assertEquals(1, pageResult.getTotal());
       assertEquals(1, pageResult.getList().size());
       assertPojoEquals(dbTaskApply, pageResult.getList().get(0));
    }

    @Test
    @Disabled  // TODO 请修改 null 为需要的值，然后删除 @Disabled 注解
    public void testGetTaskApplyList() {
       // mock 数据
       TaskApplyDO dbTaskApply = randomPojo(TaskApplyDO.class, o -> { // 等会查询到
           o.setUserId(null);
           o.setDeptId(null);
           o.setCreator(null);
           o.setTaskId(null);
           o.setMobile(null);
           o.setStatus(null);
           o.setCreateTime(null);
       });
       taskApplyMapper.insert(dbTaskApply);
       // 测试 name 不匹配
       taskApplyMapper.insert(cloneIgnoreId(dbTaskApply, o -> o.setUserId(null)));
       // 测试 deptName 不匹配
       taskApplyMapper.insert(cloneIgnoreId(dbTaskApply, o -> o.setDeptId(null)));
       // 测试 creator 不匹配
       taskApplyMapper.insert(cloneIgnoreId(dbTaskApply, o -> o.setCreator(null)));
       // 测试 taskId 不匹配
       taskApplyMapper.insert(cloneIgnoreId(dbTaskApply, o -> o.setTaskId(null)));
       // 测试 mobile 不匹配
       taskApplyMapper.insert(cloneIgnoreId(dbTaskApply, o -> o.setMobile(null)));
       // 测试 status 不匹配
       taskApplyMapper.insert(cloneIgnoreId(dbTaskApply, o -> o.setStatus(null)));
       // 测试 createTime 不匹配
       taskApplyMapper.insert(cloneIgnoreId(dbTaskApply, o -> o.setCreateTime(null)));
       // 准备参数
       TaskApplyExportReqVO reqVO = new TaskApplyExportReqVO();
       reqVO.setUserId(null);
       reqVO.setDeptId(null);
       reqVO.setCreator(null);
       reqVO.setTaskId(null);
       reqVO.setMobile(null);
       reqVO.setStatus(null);
       reqVO.setCreateTime(buildBetweenTime(2023, 2, 1, 2023, 2, 28));

       // 调用
       List<TaskApplyDO> list = taskApplyService.getTaskApplyList(reqVO);
       // 断言
       assertEquals(1, list.size());
       assertPojoEquals(dbTaskApply, list.get(0));
    }

}
