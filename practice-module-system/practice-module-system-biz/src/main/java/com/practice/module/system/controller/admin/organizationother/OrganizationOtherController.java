package com.practice.module.system.controller.admin.organizationother;

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

import com.practice.module.system.controller.admin.organizationother.vo.*;
import com.practice.module.system.dal.dataobject.organizationother.OrganizationOtherDO;
import com.practice.module.system.convert.organizationother.OrganizationOtherConvert;
import com.practice.module.system.service.organizationother.OrganizationOtherService;

@Tag(name = "管理后台 - 其他组织")
@RestController
@RequestMapping("/system/organization-other")
@Validated
public class OrganizationOtherController {

    @Resource
    private OrganizationOtherService organizationOtherService;

    @PostMapping("/create")
    @Operation(summary = "创建其他组织")
    @PreAuthorize("@ss.hasPermission('system:organization-other:create')")
    public CommonResult<Long> createOrganizationOther(@Valid @RequestBody OrganizationOtherCreateReqVO createReqVO) {
        return success(organizationOtherService.createOrganizationOther(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新其他组织")
    @PreAuthorize("@ss.hasPermission('system:organization-other:update')")
    public CommonResult<Boolean> updateOrganizationOther(@Valid @RequestBody OrganizationOtherUpdateReqVO updateReqVO) {
        organizationOtherService.updateOrganizationOther(updateReqVO);
        return success(true);
    }

    @GetMapping("/stop_service")
    @Operation(summary = "停止服务")
    @PreAuthorize("@ss.hasPermission('system:organization-company:update')")
    public CommonResult<Boolean> stopServiceOrganizationOther(@RequestParam("id") Long id) {
        organizationOtherService.stopServiceOrganizationOther(id);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除其他组织")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('system:organization-other:delete')")
    public CommonResult<Boolean> deleteOrganizationOther(@RequestParam("id") Long id) {
        organizationOtherService.deleteOrganizationOther(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得其他组织")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('system:organization-other:query')")
    public CommonResult<OrganizationOtherRespVO> getOrganizationOther(@RequestParam("id") Long id) {
        OrganizationOtherDO organizationOther = organizationOtherService.getOrganizationOther(id);
        return success(OrganizationOtherConvert.INSTANCE.convert(organizationOther));
    }

    @GetMapping("/list")
    @Operation(summary = "获得其他组织列表")
    @Parameter(name = "ids", description = "编号列表", required = true, example = "1024,2048")
    @PreAuthorize("@ss.hasPermission('system:organization-other:query')")
    public CommonResult<List<OrganizationOtherRespVO>> getOrganizationOtherList(@RequestParam("ids") Collection<Long> ids) {
        List<OrganizationOtherDO> list = organizationOtherService.getOrganizationOtherList(ids);
        return success(OrganizationOtherConvert.INSTANCE.convertList(list));
    }

    @GetMapping("/page")
    @Operation(summary = "获得其他组织分页")
    @PreAuthorize("@ss.hasPermission('system:organization-other:query')")
    public CommonResult<PageResult<OrganizationOtherRespVO>> getOrganizationOtherPage(@Valid OrganizationOtherPageReqVO pageVO) {
        PageResult<OrganizationOtherDO> pageResult = organizationOtherService.getOrganizationOtherPage(pageVO);
        return success(OrganizationOtherConvert.INSTANCE.convertPage(pageResult));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出其他组织 Excel")
    @PreAuthorize("@ss.hasPermission('system:organization-other:export')")
    @OperateLog(type = EXPORT)
    public void exportOrganizationOtherExcel(@Valid OrganizationOtherExportReqVO exportReqVO,
              HttpServletResponse response) throws IOException {
        List<OrganizationOtherDO> list = organizationOtherService.getOrganizationOtherList(exportReqVO);
        // 导出 Excel
        List<OrganizationOtherExcelVO> datas = OrganizationOtherConvert.INSTANCE.convertList02(list);
        ExcelUtils.write(response, "其他组织.xls", "数据", OrganizationOtherExcelVO.class, datas);
    }

}
