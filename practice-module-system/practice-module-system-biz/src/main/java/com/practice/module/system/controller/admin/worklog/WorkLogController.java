package com.practice.module.system.controller.admin.worklog;

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

import com.practice.module.system.controller.admin.worklog.vo.*;
import com.practice.module.system.dal.dataobject.worklog.WorkLogDO;
import com.practice.module.system.convert.worklog.WorkLogConvert;
import com.practice.module.system.service.worklog.WorkLogService;

@Tag(name = "管理后台 - 工作日志")
@RestController
@RequestMapping("/system/work-log")
@Validated
public class WorkLogController {

    @Resource
    private WorkLogService workLogService;

    @PostMapping("/create")
    @Operation(summary = "创建工作日志")
    @PreAuthorize("@ss.hasPermission('system:work-log:create')")
    public CommonResult<Long> createWorkLog(@Valid @RequestBody WorkLogCreateReqVO createReqVO) {
        return success(workLogService.createWorkLog(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新工作日志")
    @PreAuthorize("@ss.hasPermission('system:work-log:update')")
    public CommonResult<Boolean> updateWorkLog(@Valid @RequestBody WorkLogUpdateReqVO updateReqVO) {
        workLogService.updateWorkLog(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除工作日志")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('system:work-log:delete')")
    public CommonResult<Boolean> deleteWorkLog(@RequestParam("id") Long id) {
        workLogService.deleteWorkLog(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得工作日志")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('system:work-log:query')")
    public CommonResult<WorkLogRespVO> getWorkLog(@RequestParam("id") Long id) {
        WorkLogDO workLog = workLogService.getWorkLog(id);
        return success(WorkLogConvert.INSTANCE.convert(workLog));
    }

    @GetMapping("/list")
    @Operation(summary = "获得工作日志列表")
    @Parameter(name = "ids", description = "编号列表", required = true, example = "1024,2048")
    @PreAuthorize("@ss.hasPermission('system:work-log:query')")
    public CommonResult<List<WorkLogRespVO>> getWorkLogList(@RequestParam("ids") Collection<Long> ids) {
        List<WorkLogDO> list = workLogService.getWorkLogList(ids);
        return success(WorkLogConvert.INSTANCE.convertList(list));
    }

    @GetMapping("/page")
    @Operation(summary = "获得工作日志分页")
    @PreAuthorize("@ss.hasPermission('system:work-log:query')")
    public CommonResult<PageResult<WorkLogRespVO>> getWorkLogPage(@Valid WorkLogPageReqVO pageVO) {
        PageResult<WorkLogDO> pageResult = workLogService.getWorkLogPage(pageVO);
        return success(WorkLogConvert.INSTANCE.convertPage(pageResult));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出工作日志 Excel")
    @PreAuthorize("@ss.hasPermission('system:work-log:export')")
    @OperateLog(type = EXPORT)
    public void exportWorkLogExcel(@Valid WorkLogExportReqVO exportReqVO,
              HttpServletResponse response) throws IOException {
        List<WorkLogDO> list = workLogService.getWorkLogList(exportReqVO);
        // 导出 Excel
        List<WorkLogExcelVO> datas = WorkLogConvert.INSTANCE.convertList02(list);
        ExcelUtils.write(response, "工作日志.xls", "数据", WorkLogExcelVO.class, datas);
    }

}
