package com.practice.module.system.service.taskapply;

import cn.hutool.db.Page;
import com.practice.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.practice.module.system.dal.dataobject.task.TaskDO;
import com.practice.module.system.dal.dataobject.user.AdminUserDO;
import com.practice.module.system.dal.mysql.dept.DeptMapper;
import com.practice.module.system.dal.mysql.task.TaskMapper;
import com.practice.module.system.dal.mysql.user.AdminUserMapper;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import java.util.*;
import com.practice.module.system.controller.admin.taskapply.vo.*;
import com.practice.module.system.dal.dataobject.taskapply.TaskApplyDO;
import com.practice.framework.common.pojo.PageResult;

import com.practice.module.system.convert.taskapply.TaskApplyConvert;
import com.practice.module.system.dal.mysql.taskapply.TaskApplyMapper;

import static com.practice.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.practice.module.system.enums.ErrorCodeConstants.*;

/**
 * 任务申请 Service 实现类
 *
 * @author n3
 */
@Service
@Validated
public class TaskApplyServiceImpl implements TaskApplyService {

    @Resource
    private TaskApplyMapper taskApplyMapper;
    @Resource
    private AdminUserMapper adminUserMapper;
    @Resource
    private TaskMapper taskMapper;
    @Resource
    private DeptMapper deptMapper;
    @Override
    public Long createTaskApply(TaskApplyCreateReqVO createReqVO) {
        // 插入
        //根据userid查询用户
        AdminUserDO adminUserDO = adminUserMapper.selectById(createReqVO.getUserId());
        //根据taskId查询任务
        TaskDO taskDO = taskMapper.selectById(createReqVO.getTaskId());
        //获取用户姓名并写入
        createReqVO.setName(adminUserDO.getNickname());

        //校验任务存在
        if (taskDO == null ){
            throw exception(TASK_NOT_EXISTS);
        }
        //验证当前任务是否可申请 ,验证任务状态是否为开放中
        if (taskDO.getStatus() != 1) {
            throw exception(TASK_STATUS_ALLPY_ERROR);
        }
        //检验是否存在对当前任务的申请,每个用户对同一任务只能存在一个状态为待审核的
        if(taskApplyMapper.selectByTaskIdAndUserId(
                createReqVO.getTaskId(),createReqVO.getUserId()) != null){
            throw exception(TASK_APPLY_REPEAT);
        }


        TaskApplyDO taskApply = TaskApplyConvert.INSTANCE.convert(createReqVO);
        //任务申请的初始状态设置为0 审核中
        taskApply.setStatus((byte) 0);
        taskApplyMapper.insert(taskApply);
        // 返回
        return taskApply.getId();
    }

    @Override
    public void updateTaskApply(TaskApplyUpdateReqVO updateReqVO) {
        // 校验存在
        validateTaskApplyExists(updateReqVO.getId());
        // 更新
        TaskApplyDO updateObj = TaskApplyConvert.INSTANCE.convert(updateReqVO);
        taskApplyMapper.updateById(updateObj);
    }

    @Override
    public void deleteTaskApply(Long id) {
        // 校验存在
        validateTaskApplyExists(id);
        // 删除
        taskApplyMapper.deleteById(id);
    }

    private void validateTaskApplyExists(Long id) {
        if (taskApplyMapper.selectById(id) == null) {
            throw exception(TASK_APPLY_NOT_EXISTS);
        }
    }

    @Override
    public TaskApplyDO getTaskApply(Long id) {
        //       TaskApplyDO taskApplyDO =
//        //creator --> user --> deptId --> deptName --> setDeptName
//        taskApplyDO.setDeptName(deptMapper.selectById(
//                adminUserMapper.selectById(taskApplyDO.getCreator()).getDeptId()).getName());
        return taskApplyMapper.selectById(id);
    }
    @Override
    public List<TaskApplyDO> getTaskApplyListWithUserId(Long UserId) {
        //       TaskApplyDO taskApplyDO =
//        //creator --> user --> deptId --> deptName --> setDeptName
//        taskApplyDO.setDeptName(deptMapper.selectById(
//                adminUserMapper.selectById(taskApplyDO.getCreator()).getDeptId()).getName());
        return taskApplyMapper.selectByUserId(UserId);
    }

    @Override
    public List<TaskApplyDO> getTaskApplyList(Collection<Long> ids) {
        List<TaskApplyDO> taskApplyDOS = taskApplyMapper.selectBatchIds(ids);
//        for (int i = 0; i < taskApplyDOS.size(); i++) {
//
//            //creator --> user --> deptId --> deptName
//            String deptName = deptMapper.selectById(
//                    adminUserMapper.selectById(taskApplyDOS.get(i).getCreator()).getDeptId()).getName();
//            //System.out.println("部门名"+deptName);
//            //填写个人部门名称
//            taskApplyDOS.get(i).setDeptName(deptName);
//            //填写任务名称
//
//
//        }
        return taskApplyDOS;
    }

    @Override
    public PageResult<TaskApplyDO> getTaskApplyPage(TaskApplyPageReqVO pageReqVO) {
        PageResult<TaskApplyDO> taskApplyDOPageResult = taskApplyMapper.selectPage(pageReqVO);
//        for (int i = 0; i < taskApplyDOPageResult.getList().size(); i++) {
//            String deptName = deptMapper.selectById(adminUserMapper.selectById(
//                    taskApplyDOPageResult.getList().get(i).getCreator()).getDeptId()).getName();
//            taskApplyDOPageResult.getList().get(i).setDeptName(deptName);
//        }
        return taskApplyDOPageResult;
    }

    @Override
    public List<TaskApplyDO> getTaskApplyList(TaskApplyExportReqVO exportReqVO) {
        List<TaskApplyDO> taskApplyDOS = taskApplyMapper.selectList(exportReqVO);
//        for(int i = 0; i < taskApplyDOS.size(); i++) {
//            taskApplyDOS.get(i).setDeptName(deptMapper.selectById(
//                    adminUserMapper.selectById( taskApplyDOS.get(i).getCreator()).getDeptId()).getName());
//        }
        return taskApplyDOS;
    }

    @Override
    public void reviewTaskApply(TaskApplyReviewReqVO taskApplyReviewReqVO) {
        TaskApplyDO taskApplyDO = taskApplyMapper.selectById(taskApplyReviewReqVO.getId());
        //验证任务的存在性
        if (taskApplyDO == null) {
            throw exception(TASK_APPLY_NOT_EXISTS);
        }

        TaskDO taskDO = taskMapper.selectById(taskApplyDO.getTaskId());

        //验证任务申请的状态是否为待审核
        if (taskApplyDO.getStatus() != 0) {
            throw exception(TASK_APPLY_STATUS_ERROR);
        }
        if (taskApplyReviewReqVO.getFlag()) {
            //修改申请状态为已通过
            taskApplyDO.setStatus((byte) 1);
            taskApplyMapper.updateById(taskApplyDO);
            //任务需要人数减一
            taskDO.setNumberPeople(taskDO.getNumberPeople() - 1);
            System.out.println(taskDO.getNumberPeople()+"你好");

            //如果当前任务的需求人数变成0了，就修改任务状态为已确认人选
            if (taskDO.getNumberPeople() == 0) {
                //任务需求人数为0
                taskDO.setStatus((byte) 2);
                taskMapper.updateById(taskDO);
                // 同时还要设置其他申请状态变成为通过
                //1 查询该taskId，状态为0的申请列表
                List<TaskApplyDO> taskApplyDOS = taskApplyMapper.selectList(taskApplyDO.getTaskId(), (byte)0);
                for (int i = 0; i < taskApplyDOS.size(); i++) {
                    taskApplyDOS.get(i).setStatus((byte) 2);
                }
                taskApplyMapper.updateBatch(taskApplyDOS,taskApplyDOS.size());
            }else {
                //任务需求人数不为0
                taskMapper.updateById(taskDO);

            }

        }else {
            //修改申请状态为未通过
            taskApplyDO.setStatus((byte) 2);
            taskApplyMapper.updateById(taskApplyDO);
        }

    }

}
