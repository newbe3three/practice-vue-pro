package com.practice.module.system.controller.admin.organizationcompany;

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

import com.practice.module.system.controller.admin.organizationcompany.vo.*;
import com.practice.module.system.dal.dataobject.organizationcompany.OrganizationCompanyDO;
import com.practice.module.system.convert.organizationcompany.OrganizationCompanyConvert;
import com.practice.module.system.service.organizationcompany.OrganizationCompanyService;

@Tag(name = "管理后台 - 社会企业")
@RestController
@RequestMapping("/system/organization-company")
@Validated
public class OrganizationCompanyController {

    @Resource
    private OrganizationCompanyService organizationCompanyService;

    @PostMapping("/create")
    @Operation(summary = "创建社会企业")
    @PreAuthorize("@ss.hasPermission('system:organization-company:create')")
    public CommonResult<Long> createOrganizationCompany(@Valid @RequestBody OrganizationCompanyCreateReqVO createReqVO) {
        return success(organizationCompanyService.createOrganizationCompany(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新社会企业")
    @PreAuthorize("@ss.hasPermission('system:organization-company:update')")
    public CommonResult<Boolean> updateOrganizationCompany(@Valid @RequestBody OrganizationCompanyUpdateReqVO updateReqVO) {
        organizationCompanyService.updateOrganizationCompany(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除社会企业")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('system:organization-company:delete')")
    public CommonResult<Boolean> deleteOrganizationCompany(@RequestParam("id") Long id) {
        organizationCompanyService.deleteOrganizationCompany(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得社会企业")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('system:organization-company:query')")
    public CommonResult<OrganizationCompanyRespVO> getOrganizationCompany(@RequestParam("id") Long id) {
        OrganizationCompanyDO organizationCompany = organizationCompanyService.getOrganizationCompany(id);
        return success(OrganizationCompanyConvert.INSTANCE.convert(organizationCompany));
    }

    @GetMapping("/list")
    @Operation(summary = "获得社会企业列表")
    @Parameter(name = "ids", description = "编号列表", required = true, example = "1024,2048")
    @PreAuthorize("@ss.hasPermission('system:organization-company:query')")
    public CommonResult<List<OrganizationCompanyRespVO>> getOrganizationCompanyList(@RequestParam("ids") Collection<Long> ids) {
        List<OrganizationCompanyDO> list = organizationCompanyService.getOrganizationCompanyList(ids);
        return success(OrganizationCompanyConvert.INSTANCE.convertList(list));
    }

    @GetMapping("/page")
    @Operation(summary = "获得社会企业分页")
    @PreAuthorize("@ss.hasPermission('system:organization-company:query')")
    public CommonResult<PageResult<OrganizationCompanyRespVO>> getOrganizationCompanyPage(@Valid OrganizationCompanyPageReqVO pageVO) {
        PageResult<OrganizationCompanyDO> pageResult = organizationCompanyService.getOrganizationCompanyPage(pageVO);
        return success(OrganizationCompanyConvert.INSTANCE.convertPage(pageResult));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出社会企业 Excel")
    @PreAuthorize("@ss.hasPermission('system:organization-company:export')")
    @OperateLog(type = EXPORT)
    public void exportOrganizationCompanyExcel(@Valid OrganizationCompanyExportReqVO exportReqVO,
              HttpServletResponse response) throws IOException {
        List<OrganizationCompanyDO> list = organizationCompanyService.getOrganizationCompanyList(exportReqVO);
        // 导出 Excel
        List<OrganizationCompanyExcelVO> datas = OrganizationCompanyConvert.INSTANCE.convertList02(list);
        ExcelUtils.write(response, "社会企业.xls", "数据", OrganizationCompanyExcelVO.class, datas);
    }

}
