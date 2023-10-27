package com.practice.module.system.controller.admin.signin;

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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.io.IOException;

import com.practice.framework.common.pojo.PageResult;
import com.practice.framework.common.pojo.CommonResult;
import static com.practice.framework.common.pojo.CommonResult.success;

import com.practice.framework.excel.core.util.ExcelUtils;

import com.practice.framework.operatelog.core.annotations.OperateLog;
import static com.practice.framework.operatelog.core.enums.OperateTypeEnum.*;
import static com.practice.framework.security.core.util.SecurityFrameworkUtils.getLoginUserId;

import com.practice.module.system.controller.admin.signin.vo.*;
import com.practice.module.system.dal.dataobject.signin.SignInDO;
import com.practice.module.system.convert.signin.SignInConvert;
import com.practice.module.system.service.signin.SignInService;

@Tag(name = "管理后台 - 签到")
@RestController
@RequestMapping("/system/sign-in")
@Validated
public class SignInController {

    @Resource
    private SignInService signInService;

    @PostMapping("/signIn")
    @Operation(summary = "创建签到")
    @PreAuthorize("@ss.hasPermission('system:sign-in:create')")
    public CommonResult<SignInRespVO> createSignIn(@Valid @RequestBody SignInCreateReqVO createReqVO) {
        createReqVO.setUserId(getLoginUserId());
        createReqVO.setSignInTime(LocalDateTime.now());
        return success(SignInConvert.INSTANCE.convert(signInService.createSignIn(createReqVO)));
    }


    @PostMapping("/query/status")
    @Operation(summary = "查询签到情况")
    @PreAuthorize("@ss.hasPermission('system:sign-in:create')")
    public CommonResult<SignInRespVO> querySignIn(@Valid @RequestBody SignInQueryByIdVO req) {
        SignInRespVO signInRespVO = SignInConvert.INSTANCE.convert(signInService.querySignIn(req));
        if(signInRespVO == null || !Objects.equals(signInRespVO.getCreateTime().toLocalDate(), LocalDate.now())){
            return success(null);
        }
        return success(signInRespVO);
    }


    @PutMapping("/signOut")
    @Operation(summary = "签退")
    @PreAuthorize("@ss.hasPermission('system:sign-in:update')")
    public CommonResult<SignInRespVO> updateSignOut(@Valid @RequestBody SignInUpdateReqVO updateReqVO) {
        updateReqVO.setUserId(getLoginUserId());
        updateReqVO.setSignOutTime(LocalDateTime.now());
        signInService.updateSignOut(updateReqVO);
        return success(SignInConvert.INSTANCE.convert(signInService.updateSignOut(updateReqVO)));
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除签到")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('system:sign-in:delete')")
    public CommonResult<Boolean> deleteSignIn(@RequestParam("id") Long id) {
        signInService.deleteSignIn(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得签到")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('system:sign-in:query')")
    public CommonResult<SignInRespVO> getSignIn(@RequestParam("id") Long id) {
        SignInDO signIn = signInService.getSignIn(id);
        return success(SignInConvert.INSTANCE.convert(signIn));
    }


    @GetMapping("/list")
    @Operation(summary = "获得签到列表")
    @Parameter(name = "ids", description = "编号列表", required = true, example = "1024,2048")
    @PreAuthorize("@ss.hasPermission('system:sign-in:query')")
    public CommonResult<List<SignInRespVO>> getSignInList(@RequestParam("ids") Collection<Long> ids) {
        List<SignInDO> list = signInService.getSignInList(ids);
        return success(SignInConvert.INSTANCE.convertList(list));
    }

    //可以获得摸一个实践的签到列表，传参是传id就可
    @GetMapping("/page")
    @Operation(summary = "获得签到分页")
    @PreAuthorize("@ss.hasPermission('system:sign-in:query')")
    public CommonResult<PageResult<SignInRespVO>> getSignInPage(@Valid SignInPageReqVO pageVO) {
        PageResult<SignInDO> pageResult = signInService.getSignInPage(pageVO);
        return success(SignInConvert.INSTANCE.convertPage(pageResult));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出签到 Excel")
    @PreAuthorize("@ss.hasPermission('system:sign-in:export')")
    @OperateLog(type = EXPORT)
    public void exportSignInExcel(@Valid SignInExportReqVO exportReqVO,
              HttpServletResponse response) throws IOException {
        List<SignInDO> list = signInService.getSignInList(exportReqVO);
        // 导出 Excel
        List<SignInExcelVO> datas = SignInConvert.INSTANCE.convertList02(list);
        ExcelUtils.write(response, "签到.xls", "数据", SignInExcelVO.class, datas);
    }

}
