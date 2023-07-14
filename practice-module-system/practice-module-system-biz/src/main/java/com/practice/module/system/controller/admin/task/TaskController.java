package com.practice.module.system.controller.admin.task;

import com.practice.framework.common.exception.ErrorCode;
import com.practice.module.system.service.tenant.TenantService;
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
    @Resource
    private AdminUserService adminUserService;
    @Resource
    private TenantService tenantService;

    @PutMapping("/update")
    @Operation(summary = "更新任务")
    @PreAuthorize("@ss.hasPermission('system:task:update')")
    public CommonResult<Boolean> updateTask(@Valid @RequestBody TaskUpdateReqVO updateReqVO) {
        updateReqVO.setCompanyId(adminUserService.getUser(getLoginUserId()).getTenantId());
        taskService.updateTask(updateReqVO);
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



    @GetMapping("/export-excel")
    @Operation(summary = "导出任务 Excel")
    @PreAuthorize("@ss.hasPermission('system:task:export')")
    @OperateLog(type = EXPORT)
    public void exportTaskExcel(@Valid TaskExportReqVO exportReqVO,
              HttpServletResponse response) throws IOException {
        List<TaskDO> list = taskService.getTaskList(exportReqVO);
        // 导出 Excel
        List<TaskExcelVO> datas = TaskConvert.INSTANCE.convertList02(list);
        for (int i=0;i<datas.size();i++) {
            // company --> companyName
            datas.get(i).setCompanyName(tenantService.getTenant(datas.get(i).getCompanyId()).getName());
            //datas.get(i).setAmount(datas.get(i).getAmount()*100);
        }


        ExcelUtils.write(response, "任务.xls", "数据", TaskExcelVO.class, datas);
    }

    //企业端接口 创建任务
    @PostMapping("/create")
    @Operation(summary = "创建任务")
    @PreAuthorize("@ss.hasPermission('system:task:create')")
    public CommonResult<Long> createTask(@Valid @RequestBody TaskCreateReqVO createReqVO) {
       //根据当前用户id 获得企业id
        createReqVO.setCompanyId(adminUserService.getUser(getLoginUserId()).getTenantId());
        return success(taskService.createTask(createReqVO));

    }
    //平台端接口 通过任务申请
    @PutMapping("/review")
    @Operation(summary = "通过任务申请")
    @PreAuthorize("@ss.hasPermission('system:task:review')")
    public CommonResult<Boolean> reviewTask(@Valid @RequestBody TaskReviewReqVO reviewReqVO) {

        taskService.reviewTask(reviewReqVO);
        return success(true);
    }

    //平台端接口 驳回任务申请
    @PutMapping("/reject")
    @Operation(summary = "驳回任务申请")
    @PreAuthorize("@ss.hasPermission('system:task:review')")
    public CommonResult<Boolean> rejectTask(@Valid @RequestBody TaskRejectReqVO rejectReqVO) {

        taskService.rejectTask(rejectReqVO);
        return success(true);
    }

    //根据id获取任务的信息
    @GetMapping("/get")
    @Operation(summary = "获得任务")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('system:task:query')")
    public CommonResult<TaskRespVO> getTask(@RequestParam("id") Long id) {
        TaskDO task = taskService.getTask(id);
        TaskRespVO taskRespVO = TaskConvert.INSTANCE.convert(task);
        // company --> companyName
        taskRespVO.setCompanyName(tenantService.getTenant(task.getCompanyId()).getName());
        return success(taskRespVO);
    }

    @GetMapping("/list")
    @Operation(summary = "获得任务列表")
    @Parameter(name = "ids", description = "编号列表", required = true, example = "1024,2048")
    @PreAuthorize("@ss.hasPermission('system:task:query')")
    public CommonResult<List<TaskRespVO>> getTaskList(@RequestParam("ids") Collection<Long> ids) {
        List<TaskDO> list = taskService.getTaskList(ids);
        List<TaskRespVO> taskRespVOS = TaskConvert.INSTANCE.convertList(list);
        for (int i=0;i<taskRespVOS.size();i++) {
            // company --> companyName
            taskRespVOS.get(i).setCompanyName(tenantService.getTenant(taskRespVOS.get(i).getCompanyId()).getName());
        }


        return success(taskRespVOS);
    }

    @GetMapping("/page")
    @Operation(summary = "获得任务分页")
    @PreAuthorize("@ss.hasPermission('system:task:query')")
    public CommonResult<PageResult<TaskRespVO>> getTaskPage(@Valid TaskPageReqVO pageVO) {
        PageResult<TaskDO> pageResult = taskService.getTaskPage(pageVO);
        PageResult<TaskRespVO> result = TaskConvert.INSTANCE.convertPage(pageResult);
        for (int i=0;i<result.getList().size();i++) {
            result.getList().get(i).setCompanyName(tenantService.getTenant(
                    result.getList().get(i).getCompanyId()).getName());
        }

        return success(result);
    }
}
