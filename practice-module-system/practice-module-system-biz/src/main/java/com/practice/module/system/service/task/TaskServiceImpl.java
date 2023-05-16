package com.practice.module.system.service.task;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import java.util.*;
import com.practice.module.system.controller.admin.task.vo.*;
import com.practice.module.system.dal.dataobject.task.TaskDO;
import com.practice.framework.common.pojo.PageResult;

import com.practice.module.system.convert.task.TaskConvert;
import com.practice.module.system.dal.mysql.task.TaskMapper;

import static com.practice.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.practice.module.system.enums.ErrorCodeConstants.*;

/**
 * 任务 Service 实现类
 *
 * @author n3
 */
@Service
@Validated
public class TaskServiceImpl implements TaskService {

    @Resource
    private TaskMapper taskMapper;

    @Override
    public Long createTask(TaskCreateReqVO createReqVO) {
        // 插入
        //TODO 时间校验
//        System.out.println(createReqVO.getStartTime());
//        System.out.println(createReqVO.getEndTime());
        int time = createReqVO.getEndTime().compareTo(createReqVO.getStartTime());
        if (time < 0) {
            //结束时间小于开始时间 抛出结束时间小于开始时间异常
            throw exception(TASK_CREATE_TIME_ERROR);
        }
        if (createReqVO.getNumberPeople() <= 0) {
            //人数等于0 抛出人数错误异常
            throw exception(TASK_CREATE_PEOPLE_NUMBER_ERROR);
        }
        TaskDO task = TaskConvert.INSTANCE.convert(createReqVO);
        //前端获得的用金额单位为元，佣金金额以分为单位存入数据库中
        task.setAmount(task.getAmount() * 100);
        //企业或学校上报任务时，不可以提交驳回建议的数据，在后端一律改为空
        task.setSuggestion("");

        taskMapper.insert(task);
        return task.getId();

    }

    @Override
    public void updateTask(TaskUpdateReqVO updateReqVO) {
        // 校验存在
        validateTaskExists(updateReqVO.getId());
        // 更新
        TaskDO updateObj = TaskConvert.INSTANCE.convert(updateReqVO);

        taskMapper.updateById(updateObj);
    }

    //审核通过，修改状态
    @Override
    public void reviewTask(TaskReviewReqVO reviewReqVO) {
        // 校验存在
        validateTaskExists(reviewReqVO.getId());
        TaskDO reviewObj = TaskConvert.INSTANCE.convert(reviewReqVO);
        //验证任务审核状态：现根据id查询状态，再比较

        if (taskMapper.selectById(reviewObj.getId()).getStatus() != 0) {
            throw exception(TASK_REVIEW_ERROR);
        }
        //修改状态为已开放
        reviewObj.setStatus((byte) 1);
        reviewObj.setSuggestion("");
        taskMapper.updateById(reviewObj);
    }

    //审核不通过，添加驳回意见和修改状态
    @Override
    public void rejectTask(TaskRejectReqVO rejectReqVO) {
        // 校验存在
        validateTaskExists(rejectReqVO.getId());

        //修改状态
        TaskDO rejectObj = TaskConvert.INSTANCE.convert(rejectReqVO);
        //验证任务审核状态：现根据id查询状态，再比较
        if (taskMapper.selectById(rejectObj.getId()).getStatus() != 0) {
            throw exception(TASK_REVIEW_ERROR);
        }
        rejectObj.setStatus((byte) 3);
        taskMapper.updateById(rejectObj);
    }

    @Override
    public void reviewTask(TaskUpdateReqVO updateReqVO) {
        // 校验存在
        validateTaskExists(updateReqVO.getId());
        // 更新
        TaskDO updateObj = TaskConvert.INSTANCE.convert(updateReqVO);
        taskMapper.updateById(updateObj);
    }
    @Override
    public void deleteTask(Long id) {
        // 校验存在
        validateTaskExists(id);
        // 删除
        taskMapper.deleteById(id);
    }

    public void validateTaskExists(Long id) {
        if (taskMapper.selectById(id) == null) {
            throw exception(TASK_NOT_EXISTS);
        }
    }

    @Override
    public TaskDO getTask(Long id) {
        TaskDO taskDO = taskMapper.selectById(id);
        //实现任务金额的单位转换，从数据库的分到前端元
        double amount = taskDO.getAmount() * 0.01;
        taskDO.setAmount(amount);
        return taskDO;
    }

    @Override
    public List<TaskDO> getTaskList(Collection<Long> ids) {
        List<TaskDO> taskDOList = taskMapper.selectBatchIds(ids);
        for (int i = 0; i < taskDOList.size(); i++) {
            //实现任务金额的单位转换，从数据库的分到前端元
            Double amount = taskDOList.get(i).getAmount() * 0.01;
            taskDOList.get(i).setAmount(amount);
        }
        return taskDOList;
    }

    @Override
    public PageResult<TaskDO> getTaskPage(TaskPageReqVO pageReqVO) {
        PageResult<TaskDO> pageResult = taskMapper.selectPage(pageReqVO);
        for (int i = 0; i < pageResult.getList().size(); i++) {
            //实现任务金额的单位转换，从数据库的分到前端元
            Double amount = pageResult.getList().get(i).getAmount() * 0.01;
            pageResult.getList().get(i).setAmount(amount);
        }
        return pageResult;
    }

    @Override
    public List<TaskDO> getTaskList(TaskExportReqVO exportReqVO) {
        List<TaskDO> taskDOList = taskMapper.selectList(exportReqVO);
        for (int i = 0; i < taskDOList.size(); i++) {
            //实现任务金额的单位转换，从数据库的分到前端元

            Double amount = taskDOList.get(i).getAmount() * 0.01;
            taskDOList.get(i).setAmount(amount);
        }
        return taskMapper.selectList(exportReqVO);
    }


}
