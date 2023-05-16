package com.practice.module.system.service.task;

import java.util.*;
import javax.validation.*;
import com.practice.module.system.controller.admin.task.vo.*;
import com.practice.module.system.dal.dataobject.task.TaskDO;
import com.practice.framework.common.pojo.PageResult;

/**
 * 任务 Service 接口
 *
 * @author n3
 */
public interface TaskService {

    /**
     * 创建任务
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createTask(@Valid TaskCreateReqVO createReqVO);

    /**
     * 更新任务
     *
     * @param updateReqVO 更新信息
     */
    void updateTask(@Valid TaskUpdateReqVO updateReqVO);

    void reviewTask(TaskReviewReqVO reviewReqVO);

    void rejectTask(TaskRejectReqVO rejectReqVO);

    void reviewTask(TaskUpdateReqVO updateReqVO);

    /**
     * 删除任务
     *
     * @param id 编号
     */
    void deleteTask(Long id);

    /**
     * 获得任务
     *
     * @param id 编号
     * @return 任务
     */
    TaskDO getTask(Long id);

    /**
     * 获得任务列表
     *
     * @param ids 编号
     * @return 任务列表
     */
    List<TaskDO> getTaskList(Collection<Long> ids);

    /**
     * 获得任务分页
     *
     * @param pageReqVO 分页查询
     * @return 任务分页
     */
    PageResult<TaskDO> getTaskPage(TaskPageReqVO pageReqVO);

    /**
     * 获得任务列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 任务列表
     */
    List<TaskDO> getTaskList(TaskExportReqVO exportReqVO);

}
