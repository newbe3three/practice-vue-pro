package com.practice.module.system.controller.admin.organizationschool;

import com.practice.module.system.controller.admin.organizationcompany.vo.OrganizationCompanyUpdateReqVO;
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

import com.practice.module.system.controller.admin.organizationschool.vo.*;
import com.practice.module.system.dal.dataobject.organizationschool.OrganizationSchoolDO;
import com.practice.module.system.convert.organizationschool.OrganizationSchoolConvert;
import com.practice.module.system.service.organizationschool.OrganizationSchoolService;

@Tag(name = "管理后台 - 教育院校")
@RestController
@RequestMapping("/system/organization-school")
@Validated
public class OrganizationSchoolController {

    @Resource
    private OrganizationSchoolService organizationSchoolService;

    @PostMapping("/create")
    @Operation(summary = "创建教育院校")
    @PreAuthorize("@ss.hasPermission('system:organization-school:create')")
    public CommonResult<Long> createOrganizationSchool(@Valid @RequestBody OrganizationSchoolCreateReqVO createReqVO) {
        return success(organizationSchoolService.createOrganizationSchool(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新教育院校")
    @PreAuthorize("@ss.hasPermission('system:organization-school:update')")
    public CommonResult<Boolean> updateOrganizationSchool(@Valid @RequestBody OrganizationSchoolUpdateReqVO updateReqVO) {
        organizationSchoolService.updateOrganizationSchool(updateReqVO);
        return success(true);
    }

    @PutMapping("/update_sign")
    @Operation(summary = "更新服务约定")
    @PreAuthorize("@ss.hasPermission('system:organization-school:update')")
    public CommonResult<Boolean> updateSignOrganizationSchool(@Valid @RequestBody OrganizationSchoolUpdateReqVO updateReqVO) {
        organizationSchoolService.updateSignOrganizationSchool(updateReqVO);
        return success(true);
    }

    @GetMapping("/stop_service")
    @Operation(summary = "停止服务")
    @PreAuthorize("@ss.hasPermission('system:organization-company:update')")
    public CommonResult<Boolean> stopServiceOrganizationOther(@RequestParam("id") Long id) {
        organizationSchoolService.stopServiceOrganizationSchool(id);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除教育院校")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('system:organization-school:delete')")
    public CommonResult<Boolean> deleteOrganizationSchool(@RequestParam("id") Long id) {
        organizationSchoolService.deleteOrganizationSchool(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得教育院校")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('system:organization-school:query')")
    public CommonResult<OrganizationSchoolRespVO> getOrganizationSchool(@RequestParam("id") Long id) {
        OrganizationSchoolDO organizationSchool = organizationSchoolService.getOrganizationSchool(id);
        return success(OrganizationSchoolConvert.INSTANCE.convert(organizationSchool));
    }

    @GetMapping("/list")
    @Operation(summary = "获得教育院校列表")
    @Parameter(name = "ids", description = "编号列表", required = true, example = "1024,2048")
    @PreAuthorize("@ss.hasPermission('system:organization-school:query')")
    public CommonResult<List<OrganizationSchoolRespVO>> getOrganizationSchoolList(@RequestParam("ids") Collection<Long> ids) {
        List<OrganizationSchoolDO> list = organizationSchoolService.getOrganizationSchoolList(ids);
        return success(OrganizationSchoolConvert.INSTANCE.convertList(list));
    }

    @GetMapping("/page")
    @Operation(summary = "获得教育院校分页")
    @PreAuthorize("@ss.hasPermission('system:organization-school:query')")
    public CommonResult<PageResult<OrganizationSchoolRespVO>> getOrganizationSchoolPage(@Valid OrganizationSchoolPageReqVO pageVO) {
        PageResult<OrganizationSchoolDO> pageResult = organizationSchoolService.getOrganizationSchoolPage(pageVO);
        return success(OrganizationSchoolConvert.INSTANCE.convertPage(pageResult));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出教育院校 Excel")
    @PreAuthorize("@ss.hasPermission('system:organization-school:export')")
    @OperateLog(type = EXPORT)
    public void exportOrganizationSchoolExcel(@Valid OrganizationSchoolExportReqVO exportReqVO,
              HttpServletResponse response) throws IOException {
        List<OrganizationSchoolDO> list = organizationSchoolService.getOrganizationSchoolList(exportReqVO);
        // 导出 Excel
        List<OrganizationSchoolExcelVO> datas = OrganizationSchoolConvert.INSTANCE.convertList02(list);
        ExcelUtils.write(response, "教育院校.xls", "数据", OrganizationSchoolExcelVO.class, datas);
    }

}
