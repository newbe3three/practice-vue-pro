package com.practice.module.system.controller.admin.task;

import com.practice.framework.common.exception.ErrorCode;
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

import static com.practice.framework.common.pojo.CommonResult.error;
import static com.practice.framework.common.pojo.CommonResult.success;

import com.practice.framework.excel.core.util.ExcelUtils;

import com.practice.framework.operatelog.core.annotations.OperateLog;
import static com.practice.framework.operatelog.core.enums.OperateTypeEnum.*;

import com.practice.module.system.controller.admin.task.vo.*;
import com.practice.module.system.dal.dataobject.task.TaskDO;
import com.practice.module.system.convert.task.TaskConvert;
import com.practice.module.system.service.task.TaskService;

import static com.practice.framework.security.core.util.SecurityFrameworkUtils.getLoginUserId;

@Tag(name = "管理后台 - 任务")
@RestController
@RequestMapping("/system/task")
@Validated
public class TaskController {

    @Resource
    private TaskService taskService;

    @PostMapping("/create")
    @Operation(summary = "创建任务")
    @PreAuthorize("@ss.hasPermission('system:task:create')")
    public CommonResult<Long> createTask(@Valid @RequestBody TaskCreateReqVO createReqVO) {
        System.out.println(getLoginUserId());
        return success(taskService.createTask(createReqVO));
      //  Long id = taskService.createTask(createReqVO);
//        if (id != null) {
//            return success(id);
//        }else {
//            return error(TASK_CREATE_TIME_ERROR);
//        }

    }

    @PutMapping("/update")
    @Operation(summary = "更新任务")
    @PreAuthorize("@ss.hasPermission('system:task:update')")
    public CommonResult<Boolean> updateTask(@Valid @RequestBody TaskUpdateReqVO updateReqVO) {
        taskService.updateTask(updateReqVO);
        return success(true);
    }
// 通过：需要把权限插入到system_menu表中
    @PutMapping("/review")
    @Operation(summary = "任务审核通过")
    @PreAuthorize("@ss.hasPermission('system:task:review')")
    public CommonResult<Boolean> reviewTask(@Valid @RequestBody TaskReviewReqVO reviewReqVO) {

        taskService.reviewTask(reviewReqVO);
        return success(true);
    }

    // 驳回
    @PutMapping("/reject")
    @Operation(summary = "任务审核驳回建议")
    @PreAuthorize("@ss.hasPermission('system:task:reject')")
    public CommonResult<Boolean> rejectTask(@Valid @RequestBody TaskRejectReqVO rejectReqVO) {

        taskService.rejectTask(rejectReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除任务")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('system:task:delete')")
    public CommonResult<Boolean> deleteTask(@RequestParam("id") Long id) {
        taskService.deleteTask(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得任务")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('system:task:query')")
    public CommonResult<TaskRespVO> getTask(@RequestParam("id") Long id) {
        TaskDO task = taskService.getTask(id);
        return success(TaskConvert.INSTANCE.convert(task));
    }

    @GetMapping("/list")
    @Operation(summary = "获得任务列表")
    @Parameter(name = "ids", description = "编号列表", required = true, example = "1024,2048")
    @PreAuthorize("@ss.hasPermission('system:task:query')")
    public CommonResult<List<TaskRespVO>> getTaskList(@RequestParam("ids") Collection<Long> ids) {
        List<TaskDO> list = taskService.getTaskList(ids);
        System.out.println(list);
        return success(TaskConvert.INSTANCE.convertList(list));
    }

    @GetMapping("/page")
    @Operation(summary = "获得任务分页")
    @PreAuthorize("@ss.hasPermission('system:task:query')")
    public CommonResult<PageResult<TaskRespVO>> getTaskPage(@Valid TaskPageReqVO pageVO) {
        PageResult<TaskDO> pageResult = taskService.getTaskPage(pageVO);
        return success(TaskConvert.INSTANCE.convertPage(pageResult));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出任务 Excel")
    @PreAuthorize("@ss.hasPermission('system:task:export')")
    @OperateLog(type = EXPORT)
    public void exportTaskExcel(@Valid TaskExportReqVO exportReqVO,
              HttpServletResponse response) throws IOException {
        List<TaskDO> list = taskService.getTaskList(exportReqVO);
        // 导出 Excel
        List<TaskExcelVO> datas = TaskConvert.INSTANCE.convertList02(list);
        ExcelUtils.write(response, "任务.xls", "数据", TaskExcelVO.class, datas);
    }

}
