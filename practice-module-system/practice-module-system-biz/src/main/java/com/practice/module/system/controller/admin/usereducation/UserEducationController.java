package com.practice.module.system.controller.admin.usereducation;

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

import com.practice.module.system.controller.admin.usereducation.vo.*;
import com.practice.module.system.dal.dataobject.usereducation.UserEducationDO;
import com.practice.module.system.convert.usereducation.UserEducationConvert;
import com.practice.module.system.service.usereducation.UserEducationService;
import static com.practice.framework.security.core.util.SecurityFrameworkUtils.getLoginUserId;

@Tag(name = "管理后台 - 教育经历")
@RestController
@RequestMapping("/system/user-education")
@Validated
public class UserEducationController {

    @Resource
    private UserEducationService userEducationService;




    @GetMapping("/get")
    @Operation(summary = "获得教育经历")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('system:user-education:query')")
    public CommonResult<UserEducationRespVO> getUserEducation(@RequestParam("id") Long id) {
        UserEducationDO userEducation = userEducationService.getUserEducation(id);
        return success(UserEducationConvert.INSTANCE.convert(userEducation));
    }

    @GetMapping("/list")
    @Operation(summary = "获得教育经历列表")
    @Parameter(name = "ids", description = "编号列表", required = true, example = "1024,2048")
    @PreAuthorize("@ss.hasPermission('system:user-education:query')")
    public CommonResult<List<UserEducationRespVO>> getUserEducationList(@RequestParam("ids") Collection<Long> ids) {
        List<UserEducationDO> list = userEducationService.getUserEducationList(ids);
        return success(UserEducationConvert.INSTANCE.convertList(list));
    }

    @GetMapping("/page")
    @Operation(summary = "获得教育经历分页")
    @PreAuthorize("@ss.hasPermission('system:user-education:query')")
    public CommonResult<PageResult<UserEducationRespVO>> getUserEducationPage(@Valid UserEducationPageReqVO pageVO) {
        PageResult<UserEducationDO> pageResult = userEducationService.getUserEducationPage(pageVO);
        return success(UserEducationConvert.INSTANCE.convertPage(pageResult));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出教育经历 Excel")
    @PreAuthorize("@ss.hasPermission('system:user-education:export')")
    @OperateLog(type = EXPORT)
    public void exportUserEducationExcel(@Valid UserEducationExportReqVO exportReqVO,
              HttpServletResponse response) throws IOException {
        List<UserEducationDO> list = userEducationService.getUserEducationList(exportReqVO);
        // 导出 Excel
        List<UserEducationExcelVO> datas = UserEducationConvert.INSTANCE.convertList02(list);
        ExcelUtils.write(response, "教育经历.xls", "数据", UserEducationExcelVO.class, datas);
    }


    // 学生端接口：为自己创建新的教育经历
    @PostMapping("/create")
    @Operation(summary = "创建教育经历")
    @PreAuthorize("@ss.hasPermission('system:user-education:create')")
    public CommonResult<Long> createUserEducation(@Valid @RequestBody UserEducationCreateReqVO createReqVO) {
        //设置userid为当前登录用户
        createReqVO.setUserId(getLoginUserId());
        return success(userEducationService.createUserEducation(createReqVO));
    }
    // 学生端接口：修改自己的教育经历
    @PutMapping("/update")
    @Operation(summary = "更新教育经历")
    @PreAuthorize("@ss.hasPermission('system:user-education:update')")
    public CommonResult<Boolean> updateUserEducation(@Valid @RequestBody UserEducationUpdateReqVO updateReqVO) {
        //设置userid为当前登录用户
        updateReqVO.setUserId(getLoginUserId());
        userEducationService.updateUserEducation(updateReqVO);
        return success(true);
    }
    // 学生端接口：删除自己的教育经历
    @DeleteMapping("/delete")
    @Operation(summary = "删除教育经历")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('system:user-education:delete')")
    public CommonResult<Boolean> deleteUserEducation(@RequestParam("id") Long id) {
        userEducationService.deleteUserEducation(id,getLoginUserId());
        return success(true);
    }

}
