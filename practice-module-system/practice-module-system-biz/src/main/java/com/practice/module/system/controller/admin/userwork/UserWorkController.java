package com.practice.module.system.controller.admin.userwork;

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

import com.practice.module.system.controller.admin.userwork.vo.*;
import com.practice.module.system.dal.dataobject.userwork.UserWorkDO;
import com.practice.module.system.convert.userwork.UserWorkConvert;
import com.practice.module.system.service.userwork.UserWorkService;
import static com.practice.framework.security.core.util.SecurityFrameworkUtils.getLoginUserId;

@Tag(name = "管理后台 - 工作经历")
@RestController
@RequestMapping("/system/user-work")
@Validated
public class UserWorkController {

    @Resource
    private UserWorkService userWorkService;






    @GetMapping("/get")
    @Operation(summary = "获得工作经历")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('system:user-work:query')")
    public CommonResult<UserWorkRespVO> getUserWork(@RequestParam("id") Long id) {
        UserWorkDO userWork = userWorkService.getUserWork(id);
        return success(UserWorkConvert.INSTANCE.convert(userWork));
    }

    @GetMapping("/list")
    @Operation(summary = "获得工作经历列表")
    @Parameter(name = "ids", description = "编号列表", required = true, example = "1024,2048")
    @PreAuthorize("@ss.hasPermission('system:user-work:query')")
    public CommonResult<List<UserWorkRespVO>> getUserWorkList(@RequestParam("ids") Collection<Long> ids) {
        List<UserWorkDO> list = userWorkService.getUserWorkList(ids);
        return success(UserWorkConvert.INSTANCE.convertList(list));
    }

    @GetMapping("/page")
    @Operation(summary = "获得工作经历分页")
    @PreAuthorize("@ss.hasPermission('system:user-work:query')")
    public CommonResult<PageResult<UserWorkRespVO>> getUserWorkPage(@Valid UserWorkPageReqVO pageVO) {
        PageResult<UserWorkDO> pageResult = userWorkService.getUserWorkPage(pageVO);
        return success(UserWorkConvert.INSTANCE.convertPage(pageResult));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出工作经历 Excel")
    @PreAuthorize("@ss.hasPermission('system:user-work:export')")
    @OperateLog(type = EXPORT)
    public void exportUserWorkExcel(@Valid UserWorkExportReqVO exportReqVO,
              HttpServletResponse response) throws IOException {
        List<UserWorkDO> list = userWorkService.getUserWorkList(exportReqVO);
        // 导出 Excel
        List<UserWorkExcelVO> datas = UserWorkConvert.INSTANCE.convertList02(list);
        ExcelUtils.write(response, "工作经历.xls", "数据", UserWorkExcelVO.class, datas);
    }
    //学生端接口：添加工作经历
    @PostMapping("/create")
    @Operation(summary = "添加工作经历")
    @PreAuthorize("@ss.hasPermission('system:user-work:create')")
    public CommonResult<Long> createUserWork(@Valid @RequestBody UserWorkCreateReqVO createReqVO) {
        createReqVO.setUserId(getLoginUserId());
        return success(userWorkService.createUserWork(createReqVO));
    }
    //学生端接口：修改工作经历
    @PutMapping("/update")
    @Operation(summary = "修改工作经历")
    @PreAuthorize("@ss.hasPermission('system:user-work:update')")
    public CommonResult<Boolean> updateUserWork(@Valid @RequestBody UserWorkUpdateReqVO updateReqVO) {
        updateReqVO.setUserId(getLoginUserId());
        userWorkService.updateUserWork(updateReqVO);
        return success(true);
    }
    @DeleteMapping("/delete")
    @Operation(summary = "删除工作经历")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('system:user-work:delete')")
    public CommonResult<Boolean> deleteUserWork(@RequestParam("id") Long id) {
        userWorkService.deleteUserWork(id,getLoginUserId());
        return success(true);
    }
    //学生端接口：获得当前登录用户工作经历
    @GetMapping("/list/current")
    @Operation(summary = "获得当前登录用户工作经历")
    @PreAuthorize("@ss.hasPermission('system:user-work:current')")
    public CommonResult<List<UserWorkRespVO>> getUserWorkListWithLogin() {
        List<UserWorkDO> list = userWorkService.getUserWorkWithUserId(getLoginUserId());
        return success(UserWorkConvert.INSTANCE.convertList(list));
    }

    //根据userid查询工作经历
    @GetMapping("/list/userid")
    @Operation(summary = "根据userid查询工作经历")
    @Parameter(name = "userId", description = "userId", required = true, example = "1028")
    @PreAuthorize("@ss.hasPermission('system:user-work:query')")
    public CommonResult<List<UserWorkRespVO>> getUserWorkListWithUserId(@RequestParam("userId") Long userId) {

        List<UserWorkDO> list = userWorkService.getUserWorkWithUserId(userId);
        return success(UserWorkConvert.INSTANCE.convertList(list));
    }

}
