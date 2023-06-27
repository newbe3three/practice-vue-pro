package com.practice.module.system.controller.admin.taskapply;

import com.practice.module.system.dal.dataobject.dept.DeptDO;
import com.practice.module.system.service.dept.DeptService;
import com.practice.module.system.service.task.TaskService;
import com.practice.module.system.service.user.AdminUserService;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Operation;

import javax.validation.constraints.*;
import javax.validation.*;
import javax.servlet.http.*;
import java.util.*;
import java.io.IOException;

import com.practice.framework.common.pojo.PageResult;
import com.practice.framework.common.pojo.CommonResult;
import static com.practice.framework.common.pojo.CommonResult.success;

import com.practice.framework.excel.core.util.ExcelUtils;

import com.practice.framework.operatelog.core.annotations.OperateLog;
import static com.practice.framework.operatelog.core.enums.OperateTypeEnum.*;

import com.practice.module.system.controller.admin.taskapply.vo.*;
import com.practice.module.system.dal.dataobject.taskapply.TaskApplyDO;
import com.practice.module.system.convert.taskapply.TaskApplyConvert;
import com.practice.module.system.service.taskapply.TaskApplyService;

import static com.practice.framework.security.core.util.SecurityFrameworkUtils.getLoginUserId;


@Tag(name = "管理后台 - 任务申请")
@RestController
@RequestMapping("/system/task-apply")
@Validated
public class TaskApplyController {

    @Resource
    private TaskApplyService taskApplyService;
    @Resource
    private TaskService taskService;
    @Resource
    private DeptService deptService;
    @Resource
    private AdminUserService adminUserService;

    private String taskName;
    private String deptName;
    private String userName;
    @PostMapping("/create")
    @Operation(summary = "创建任务申请")
    @PreAuthorize("@ss.hasPermission('system:task-apply:create')")
    public CommonResult<Long> createTaskApply(@Valid @RequestBody TaskApplyCreateReqVO createReqVO) {
        //获取登录用户id
        createReqVO.setUserId(getLoginUserId());

        return success(taskApplyService.createTaskApply(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新任务申请")
    @PreAuthorize("@ss.hasPermission('system:task-apply:update')")
    public CommonResult<Boolean> updateTaskApply(@Valid @RequestBody TaskApplyUpdateReqVO updateReqVO) {
        taskApplyService.updateTaskApply(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除任务申请")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('system:task-apply:delete')")
    public CommonResult<Boolean> deleteTaskApply(@RequestParam("id") Long id) {
        taskApplyService.deleteTaskApply(id);
        return success(true);
    }
    @GetMapping("/get")
    @Operation(summary = "获得任务申请")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('system:task-apply:query')")
    public CommonResult<TaskApplyRespVO> getTaskApply(@RequestParam("id") Long id) {
        TaskApplyDO taskApply = taskApplyService.getTaskApply(id);
        TaskApplyRespVO taskApplyRespVO = TaskApplyConvert.INSTANCE.convert(taskApply);
        //deptService 根据deptId获取name后写入taskApplyRespVO，因为前端要name
        deptName = deptService.getDept(taskApply.getDeptId()).getName();
        //taskService 根据taskId获取name后写入taskApplyRespVO，因为前端要name
        taskName = taskService.getTask(taskApply.getTaskId()).getName();
        //adminService 根据userId获取nickname后写入taskApplyRespVO，因为前端要name
        userName = adminUserService.getUser(taskApply.getUserId()).getNickname();
        taskApplyRespVO.setDeptName(deptName);
        taskApplyRespVO.setTaskName(taskName);
        taskApplyRespVO.setUserName(userName);
        return success(taskApplyRespVO);
    }

    @GetMapping("/get/userid")
    @Operation(summary = "获得任务申请")
    @Parameter(name = "userId", description = "用户编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('system:task-apply:get:userid')")
    public CommonResult<List<TaskApplyRespVO>> getTaskApplyListWithUserId() {
        List<TaskApplyDO> taskApplyDOList = taskApplyService.getTaskApplyListWithUserId(getLoginUserId());
        List<TaskApplyRespVO> taskApplyRespVOS = TaskApplyConvert.INSTANCE.convertList(taskApplyDOList);
        //taskId 转换成 taskName 输出  deptId转换成deptName输出
        for(int i = 0; i < taskApplyRespVOS.size(); i++) {
            taskName = taskService.getTask(taskApplyRespVOS.get(i).getTaskId()).getName();
            deptName = deptService.getDept(taskApplyRespVOS.get(i).getDeptId()).getName();
            userName = adminUserService.getUser(taskApplyRespVOS.get(i).getUserId()).getNickname();
            taskApplyRespVOS.get(i).setTaskName(taskName);
            taskApplyRespVOS.get(i).setDeptName(deptName);
            taskApplyRespVOS.get(i).setUserName(userName);
        }
        return success(taskApplyRespVOS);
    }
    @PostMapping("/review")
    @Operation(summary = "任务申请审核")
    @PreAuthorize("@ss.hasPermission('system:task-apply:review')")
    public CommonResult<Boolean> reviewTaskApply(@RequestBody TaskApplyReviewReqVO taskApplyReviewReqVO) {
        taskApplyService.reviewTaskApply(taskApplyReviewReqVO);
        return success(true);
    }

    @GetMapping("/list")
    @Operation(summary = "获得任务申请列表")
    @Parameter(name = "ids", description = "编号列表", required = true, example = "1024,2048")
    @PreAuthorize("@ss.hasPermission('system:task-apply:query')")
    public CommonResult<List<TaskApplyRespVO>> getTaskApplyList(@RequestParam("ids") Collection<Long> ids) {
        List<TaskApplyDO> list = taskApplyService.getTaskApplyList(ids);
        List<TaskApplyRespVO> taskApplyRespVOS = TaskApplyConvert.INSTANCE.convertList(list);
        //taskId 转换成 taskName 输出  deptId转换成deptName输出
        for(int i = 0; i < taskApplyRespVOS.size(); i++) {
            taskName = taskService.getTask(taskApplyRespVOS.get(i).getTaskId()).getName();
            deptName = deptService.getDept(taskApplyRespVOS.get(i).getDeptId()).getName();
            userName = adminUserService.getUser(taskApplyRespVOS.get(i).getUserId()).getNickname();
            taskApplyRespVOS.get(i).setTaskName(taskName);
            taskApplyRespVOS.get(i).setDeptName(deptName);
            taskApplyRespVOS.get(i).setUserName(userName);
        }
        return success(taskApplyRespVOS);
    }

    @GetMapping("/page")
    @Operation(summary = "获得任务申请分页")
    @PreAuthorize("@ss.hasPermission('system:task-apply:query')")
    public CommonResult<PageResult<TaskApplyRespVO>> getTaskApplyPage(@Valid TaskApplyPageReqVO pageVO) {
        PageResult<TaskApplyDO> pageResult = taskApplyService.getTaskApplyPage(pageVO);
        PageResult<TaskApplyRespVO> taskApplyRespVOPageResult = TaskApplyConvert.INSTANCE.convertPage(pageResult);
        for (int i = 0; i < taskApplyRespVOPageResult.getList().size(); i++) {
            //taskId 转换成 taskName 输出  deptId转换成deptName输出
            deptName = deptService.getDept(taskApplyRespVOPageResult.getList().get(i).getDeptId()).getName();
            taskName = taskService.getTask(taskApplyRespVOPageResult.getList().get(i).getTaskId()).getName();
            userName = adminUserService.getUser(taskApplyRespVOPageResult.getList().get(i).getUserId()).getNickname();
            taskApplyRespVOPageResult.getList().get(i).setUserName(userName);
            taskApplyRespVOPageResult.getList().get(i).setDeptName(deptName);
            taskApplyRespVOPageResult.getList().get(i).setTaskName(taskName);
        }
        return success(taskApplyRespVOPageResult);
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出任务申请 Excel")
    @PreAuthorize("@ss.hasPermission('system:task-apply:export')")
    @OperateLog(type = EXPORT)
    public void exportTaskApplyExcel(@Valid TaskApplyExportReqVO exportReqVO,
              HttpServletResponse response) throws IOException {
        List<TaskApplyDO> list = taskApplyService.getTaskApplyList(exportReqVO);
        // 导出 Excel
        List<TaskApplyExcelVO> datas = TaskApplyConvert.INSTANCE.convertList02(list);
        for (int i = 0; i < datas.size(); i++) {
            //向表格中输入部门名称 和 任务名称
            deptName = deptService.getDept(datas.get(i).getDeptId()).getName();
            taskName = taskService.getTask(datas.get(i).getTaskId()).getName();
            userName = adminUserService.getUser(datas.get(i).getUserId()).getNickname();
            datas.get(i).setTaskName(taskName);
            datas.get(i).setDeptName(deptName);
            datas.get(i).setUserName(userName);

        }
        ExcelUtils.write(response, "任务申请.xls", "数据", TaskApplyExcelVO.class, datas);
    }






}
