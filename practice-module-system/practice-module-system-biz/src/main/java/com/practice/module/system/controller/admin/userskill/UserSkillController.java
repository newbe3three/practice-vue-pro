package com.practice.module.system.controller.admin.userskill;

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

import com.practice.module.system.controller.admin.userskill.vo.*;
import com.practice.module.system.dal.dataobject.userskill.UserSkillDO;
import com.practice.module.system.convert.userskill.UserSkillConvert;
import com.practice.module.system.service.userskill.UserSkillService;
import static com.practice.framework.security.core.util.SecurityFrameworkUtils.getLoginUserId;

@Tag(name = "管理后台 - 个人技能")
@RestController
@RequestMapping("/system/user-skill")
@Validated
public class UserSkillController {

    @Resource
    private UserSkillService userSkillService;

    @GetMapping("/get")
    @Operation(summary = "获得个人技能")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('system:user-skill:query')")
    public CommonResult<UserSkillRespVO> getUserSkill(@RequestParam("id") Long id) {
        UserSkillDO userSkill = userSkillService.getUserSkill(id);
        return success(UserSkillConvert.INSTANCE.convert(userSkill));
    }

    @GetMapping("/list")
    @Operation(summary = "获得个人技能列表")
    @Parameter(name = "ids", description = "编号列表", required = true, example = "1024,2048")
    @PreAuthorize("@ss.hasPermission('system:user-skill:query')")
    public CommonResult<List<UserSkillRespVO>> getUserSkillList(@RequestParam("ids") Collection<Long> ids) {
        List<UserSkillDO> list = userSkillService.getUserSkillList(ids);
        return success(UserSkillConvert.INSTANCE.convertList(list));
    }

    @GetMapping("/page")
    @Operation(summary = "获得个人技能分页")
    @PreAuthorize("@ss.hasPermission('system:user-skill:query')")
    public CommonResult<PageResult<UserSkillRespVO>> getUserSkillPage(@Valid UserSkillPageReqVO pageVO) {
        PageResult<UserSkillDO> pageResult = userSkillService.getUserSkillPage(pageVO);
        return success(UserSkillConvert.INSTANCE.convertPage(pageResult));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出个人技能 Excel")
    @PreAuthorize("@ss.hasPermission('system:user-skill:export')")
    @OperateLog(type = EXPORT)
    public void exportUserSkillExcel(@Valid UserSkillExportReqVO exportReqVO,
              HttpServletResponse response) throws IOException {
        List<UserSkillDO> list = userSkillService.getUserSkillList(exportReqVO);
        // 导出 Excel
        List<UserSkillExcelVO> datas = UserSkillConvert.INSTANCE.convertList02(list);
        ExcelUtils.write(response, "个人技能.xls", "数据", UserSkillExcelVO.class, datas);
    }

    //学生端接口：添加个人技能
    @PostMapping("/create")
    @Operation(summary = "创建个人技能")
    @PreAuthorize("@ss.hasPermission('system:user-skill:create')")
    public CommonResult<Long> createUserSkill(@Valid @RequestBody UserSkillCreateReqVO createReqVO) {
        //通过getLoginUserId获取当前登录用户id，给自己添加技能
        createReqVO.setUserId(getLoginUserId());
        return success(userSkillService.createUserSkill(createReqVO));
    }
    //学生端接口：删除个人技能
    @DeleteMapping("/delete")
    @Operation(summary = "删除个人技能")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('system:user-skill:delete')")
    public CommonResult<Boolean> deleteUserSkill(@RequestParam("id") Long id) {
        userSkillService.deleteUserSkill(id,getLoginUserId());
        return success(true);
    }
    //学生端接口：修改个人技能
    @PutMapping("/update")
    @Operation(summary = "更新个人技能")
    @PreAuthorize("@ss.hasPermission('system:user-skill:update')")
    public CommonResult<Boolean> updateUserSkill(@Valid @RequestBody UserSkillUpdateReqVO updateReqVO) {
        //通过getLoginUserId获取当前登录用户id，给自己添加技能
        updateReqVO.setUserId(getLoginUserId());
        userSkillService.updateUserSkill(updateReqVO);
        return success(true);
    }
    //学生端接口：查询当前用户的技能列表
    @GetMapping("/list/current")
    @Operation(summary = "获得个人技能")
    @PreAuthorize("@ss.hasPermission('system:user-skill:current')")
    public CommonResult<List<UserSkillRespVO>> getUserSkillWithUserId() {
        List<UserSkillDO> userSkill = userSkillService.getUserSkillWithUserId(getLoginUserId());
        return success(UserSkillConvert.INSTANCE.convertList(userSkill));
    }

    //根据userId查询个人技能
    @GetMapping("/list/userid")
    @Operation(summary = "获得个人技能")
    @Parameter(name = "userId", description = "用户编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('system:user-skill:query')")
    public CommonResult<List<UserSkillRespVO>> getUserSkillWithUserId(@RequestParam("userId") Long userId) {
        List<UserSkillDO> userSkill = userSkillService.getUserSkillWithUserId(userId);
        return success(UserSkillConvert.INSTANCE.convertList(userSkill));
    }
}
