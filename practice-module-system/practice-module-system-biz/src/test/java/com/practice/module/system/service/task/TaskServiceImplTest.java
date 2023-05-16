package com.practice.module.system.service.task;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

import javax.annotation.Resource;

import com.practice.framework.test.core.ut.BaseDbUnitTest;

import com.practice.module.system.controller.admin.task.vo.*;
import com.practice.module.system.dal.dataobject.task.TaskDO;
import com.practice.module.system.dal.mysql.task.TaskMapper;
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
* {@link TaskServiceImpl} 的单元测试类
*
* @author n3
*/
@Import(TaskServiceImpl.class)
public class TaskServiceImplTest extends BaseDbUnitTest {

    @Resource
    private TaskServiceImpl taskService;

    @Resource
    private TaskMapper taskMapper;
    @Test
    public void testGetTaskList2() {
        List<Long> list = new ArrayList<>(3);
        list.add((long)3);
        list.add((long)4);
        taskService.getTaskList(list);

    }
    @Test
    public void testCreateTask_success() {
        // 准备参数
        TaskCreateReqVO reqVO = randomPojo(TaskCreateReqVO.class);

        // 调用
        Long taskId = taskService.createTask(reqVO);
        // 断言
        assertNotNull(taskId);
        // 校验记录的属性是否正确
        TaskDO task = taskMapper.selectById(taskId);
        assertPojoEquals(reqVO, task);
    }

    @Test
    public void testUpdateTask_success() {
        // mock 数据
        TaskDO dbTask = randomPojo(TaskDO.class);
        taskMapper.insert(dbTask);// @Sql: 先插入出一条存在的数据
        // 准备参数
        TaskUpdateReqVO reqVO = randomPojo(TaskUpdateReqVO.class, o -> {
            o.setId(dbTask.getId()); // 设置更新的 ID
        });

        // 调用
        taskService.updateTask(reqVO);
        // 校验是否更新正确
        TaskDO task = taskMapper.selectById(reqVO.getId()); // 获取最新的
        assertPojoEquals(reqVO, task);
    }

    @Test
    public void testUpdateTask_notExists() {
        // 准备参数
        TaskUpdateReqVO reqVO = randomPojo(TaskUpdateReqVO.class);

        // 调用, 并断言异常
        assertServiceException(() -> taskService.updateTask(reqVO), TASK_NOT_EXISTS);
    }

    @Test
    public void testDeleteTask_success() {
        // mock 数据
        TaskDO dbTask = randomPojo(TaskDO.class);
        taskMapper.insert(dbTask);// @Sql: 先插入出一条存在的数据
        // 准备参数
        Long id = dbTask.getId();

        // 调用
        taskService.deleteTask(id);
       // 校验数据不存在了
       assertNull(taskMapper.selectById(id));
    }

    @Test
    public void testDeleteTask_notExists() {
        // 准备参数
        Long id = randomLongId();

        // 调用, 并断言异常
        assertServiceException(() -> taskService.deleteTask(id), TASK_NOT_EXISTS);
    }

    @Test
    @Disabled  // TODO 请修改 null 为需要的值，然后删除 @Disabled 注解
    public void testGetTaskPage() {
       // mock 数据
       TaskDO dbTask = randomPojo(TaskDO.class, o -> { // 等会查询到
           o.setName(null);
           o.setDeptName(null);
           o.setAmount(null);
           o.setStartTime(null);
           o.setEndTime(null);
           o.setNumberPeople(null);
           o.setStatus(null);
           o.setSuggestion(null);
           o.setCreateTime(null);
       });
       taskMapper.insert(dbTask);
       // 测试 name 不匹配
       taskMapper.insert(cloneIgnoreId(dbTask, o -> o.setName(null)));
       // 测试 deptName 不匹配
       taskMapper.insert(cloneIgnoreId(dbTask, o -> o.setDeptName(null)));
       // 测试 amount 不匹配
       taskMapper.insert(cloneIgnoreId(dbTask, o -> o.setAmount(null)));
       // 测试 startTime 不匹配
       taskMapper.insert(cloneIgnoreId(dbTask, o -> o.setStartTime(null)));
       // 测试 endTime 不匹配
       taskMapper.insert(cloneIgnoreId(dbTask, o -> o.setEndTime(null)));
       // 测试 numberPeople 不匹配
       taskMapper.insert(cloneIgnoreId(dbTask, o -> o.setNumberPeople(null)));
       // 测试 status 不匹配
       taskMapper.insert(cloneIgnoreId(dbTask, o -> o.setStatus(null)));
       // 测试 suggestion 不匹配
       taskMapper.insert(cloneIgnoreId(dbTask, o -> o.setSuggestion(null)));
       // 测试 createTime 不匹配
       taskMapper.insert(cloneIgnoreId(dbTask, o -> o.setCreateTime(null)));
       // 准备参数
       TaskPageReqVO reqVO = new TaskPageReqVO();
       reqVO.setName(null);
       reqVO.setDeptName(null);
       reqVO.setAmount(null);
       reqVO.setStartTime(buildBetweenTime(2023, 2, 1, 2023, 2, 28));
       reqVO.setEndTime(buildBetweenTime(2023, 2, 1, 2023, 2, 28));
       reqVO.setNumberPeople(null);
       reqVO.setStatus(null);
       reqVO.setSuggestion(null);
       reqVO.setCreateTime(buildBetweenTime(2023, 2, 1, 2023, 2, 28));

       // 调用
       PageResult<TaskDO> pageResult = taskService.getTaskPage(reqVO);
       // 断言
       assertEquals(1, pageResult.getTotal());
       assertEquals(1, pageResult.getList().size());
       assertPojoEquals(dbTask, pageResult.getList().get(0));
    }

    @Test
    @Disabled  // TODO 请修改 null 为需要的值，然后删除 @Disabled 注解
    public void testGetTaskList() {
       // mock 数据
       TaskDO dbTask = randomPojo(TaskDO.class, o -> { // 等会查询到
           o.setName(null);
           o.setDeptName(null);
           o.setAmount(null);
           o.setStartTime(null);
           o.setEndTime(null);
           o.setNumberPeople(null);
           o.setStatus(null);
           o.setSuggestion(null);
           o.setCreateTime(null);
       });
       taskMapper.insert(dbTask);
       // 测试 name 不匹配
       taskMapper.insert(cloneIgnoreId(dbTask, o -> o.setName(null)));
       // 测试 deptName 不匹配
       taskMapper.insert(cloneIgnoreId(dbTask, o -> o.setDeptName(null)));
       // 测试 amount 不匹配
       taskMapper.insert(cloneIgnoreId(dbTask, o -> o.setAmount(null)));
       // 测试 startTime 不匹配
       taskMapper.insert(cloneIgnoreId(dbTask, o -> o.setStartTime(null)));
       // 测试 endTime 不匹配
       taskMapper.insert(cloneIgnoreId(dbTask, o -> o.setEndTime(null)));
       // 测试 numberPeople 不匹配
       taskMapper.insert(cloneIgnoreId(dbTask, o -> o.setNumberPeople(null)));
       // 测试 status 不匹配
       taskMapper.insert(cloneIgnoreId(dbTask, o -> o.setStatus(null)));
       // 测试 suggestion 不匹配
       taskMapper.insert(cloneIgnoreId(dbTask, o -> o.setSuggestion(null)));
       // 测试 createTime 不匹配
       taskMapper.insert(cloneIgnoreId(dbTask, o -> o.setCreateTime(null)));
       // 准备参数
       TaskExportReqVO reqVO = new TaskExportReqVO();
       reqVO.setName(null);
       reqVO.setDeptName(null);
       reqVO.setAmount(null);
       reqVO.setStartTime(buildBetweenTime(2023, 2, 1, 2023, 2, 28));
       reqVO.setEndTime(buildBetweenTime(2023, 2, 1, 2023, 2, 28));
       reqVO.setNumberPeople(null);
       reqVO.setStatus(null);
       reqVO.setSuggestion(null);
       reqVO.setCreateTime(buildBetweenTime(2023, 2, 1, 2023, 2, 28));

       // 调用
       List<TaskDO> list = taskService.getTaskList(reqVO);
       // 断言
       assertEquals(1, list.size());
       assertPojoEquals(dbTask, list.get(0));
    }

}
