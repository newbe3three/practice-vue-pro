package com.practice.module.system.controller.admin.practiceapply;

import com.practice.module.system.controller.admin.practice.vo.reject.PracticeRejectCreateReqVO;
import com.practice.module.system.controller.admin.practice.vo.reject.PracticeRejectRespVO;
import com.practice.module.system.controller.admin.practiceapply.vo.apply.*;
import com.practice.module.system.controller.admin.practiceapply.vo.reject.PracticeApplyRejectCreateReqVO;
import com.practice.module.system.controller.admin.practiceapply.vo.reject.PracticeApplyRejectRespVO;
import com.practice.module.system.convert.practice.PracticeRejectConvert;
import com.practice.module.system.convert.practiceapply.PracticeApplyRejectConvert;
import com.practice.module.system.dal.dataobject.practice.PracticeDO;
import com.practice.module.system.dal.dataobject.practice.PracticeRejectDO;
import com.practice.module.system.dal.dataobject.practiceapply.PracticeApplyRejectDO;
import com.practice.module.system.service.practice.PracticeService;
import com.practice.module.system.service.tenant.TenantService;
import com.practice.module.system.service.user.AdminUserService;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Operation;

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

import com.practice.module.system.dal.dataobject.practiceapply.PracticeApplyDO;
import com.practice.module.system.convert.practiceapply.PracticeApplyConvert;
import com.practice.module.system.service.practiceapply.PracticeApplyService;


import static com.practice.framework.security.core.util.SecurityFrameworkUtils.getLoginUserId;

@Tag(name = "管理后台 - 实践申请")
@RestController
@RequestMapping("/system/practice-apply")
@Validated
public class PracticeApplyController {

    @Resource
    private PracticeApplyService practiceApplyService;
    @Resource
    private AdminUserService adminUserService;
    @Resource
    private PracticeService practiceService;
    @Resource
    private TenantService tenantService;

    @DeleteMapping("/delete")
    @Operation(summary = "删除实践申请")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('system:practice-apply:delete')")
    public CommonResult<Boolean> deletePracticeApply(@RequestParam("id") Long id) {
        practiceApplyService.deletePracticeApply(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得实践申请")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('system:practice-apply:query')")
    public CommonResult<PracticeApplyRespVO> getPracticeApply(@RequestParam("id") Long id) {
        PracticeApplyDO practiceApply = practiceApplyService.getPracticeApply(id);
        return success(PracticeApplyConvert.INSTANCE.convert(practiceApply));
    }

    @GetMapping("/list")
    @Operation(summary = "获得实践申请列表")
    @Parameter(name = "ids", description = "编号列表", required = true, example = "1024,2048")
    @PreAuthorize("@ss.hasPermission('system:practice-apply:query')")
    public CommonResult<List<PracticeApplyRespVO>> getPracticeApplyList(@RequestParam("ids") Collection<Long> ids) {
        List<PracticeApplyDO> list = practiceApplyService.getPracticeApplyList(ids);
        return success(PracticeApplyConvert.INSTANCE.convertList(list));
    }

    @GetMapping("/page")
    @Operation(summary = "获得实践申请分页")
    @PreAuthorize("@ss.hasPermission('system:practice-apply:query')")
    public CommonResult<PageResult<PracticeApplyRespVO>> getPracticeApplyPage(@Valid PracticeApplyPageReqVO pageVO) {
        System.out.println("123"+pageVO);
        PageResult<PracticeApplyDO> pageResult = practiceApplyService.getPracticeApplyPage(pageVO);
        return success(PracticeApplyConvert.INSTANCE.convertPage(pageResult));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出实践申请 Excel")
    @PreAuthorize("@ss.hasPermission('system:practice-apply:export')")
    @OperateLog(type = EXPORT)
    public void exportPracticeApplyExcel(@Valid PracticeApplyExportReqVO exportReqVO,
              HttpServletResponse response) throws IOException {
        List<PracticeApplyDO> list = practiceApplyService.getPracticeApplyList(exportReqVO);
        // 导出 Excel
        List<PracticeApplyExcelVO> datas = PracticeApplyConvert.INSTANCE.convertList02(list);
        ExcelUtils.write(response, "实践申请.xls", "数据", PracticeApplyExcelVO.class, datas);
    }

    // 学生端接口 学生端发起对实践的申请
    @PostMapping("/student/create")
    @Operation(summary = "创建实践申请")
    @PreAuthorize("@ss.hasPermission('system:practice-apply:student:create')")
    public CommonResult<Long> createPracticeApply(@Valid @RequestBody PracticeApplyCreateReqVO createReqVO) {
        PracticeApplyDO practiceApply = PracticeApplyConvert.INSTANCE.convert2(createReqVO);
        practiceApply.setUserId(getLoginUserId());
        return success(practiceApplyService.createPracticeApply(practiceApply));
    }
    //学生端接口 学生端修改或修改被驳回的申请
    @PutMapping("/student/update")
    @Operation(summary = "更新实践申请")
    @PreAuthorize("@ss.hasPermission('system:practice-apply:student:update')")
    public CommonResult<Boolean> updatePracticeApply(@Valid @RequestBody PracticeApplyUpdateReqVO updateReqVO) {
        PracticeApplyDO practiceApply = PracticeApplyConvert.INSTANCE.convert2(updateReqVO);

        practiceApplyService.updatePracticeApply(practiceApply);
        return success(true);
    }
    //学生端接口 学生查询自己发起的申请
    @GetMapping("/student/page")
    @Operation(summary = "获得实践申请分页")
    @PreAuthorize("@ss.hasPermission('system:practice-apply:student:page')")
    public CommonResult<PageResult<PracticeApplyRespVO>> studentGetPracticeApply(@Valid PracticeApplyPageReqVO pageVO) {
        // 根据当前登录用户的userid 查询申请
        pageVO.setUserId(getLoginUserId());
        PageResult<PracticeApplyDO> pageResult = practiceApplyService.getPracticeApplyPage(pageVO);
        PageResult<PracticeApplyRespVO> result = PracticeApplyConvert.INSTANCE.convertPage(pageResult);
        // userid --> nickname practiceId --> companyName - practiceName
        for (int i=0;i<result.getList().size();i++) {
            result.getList().get(i).setPracticeName(adminUserService.getUser(getLoginUserId()).getNickname());
            PracticeDO practice = practiceService.getPractice(result.getList().get(i).getPracticeId());
            result.getList().get(i).setPracticeName(tenantService.getTenant(practice.getCompanyId()).getName()
                    + "-" +practice.getName());
        }

        return success(result);
    }
    //企业端接口 查询学生对一实践的申请驳回记录
    @GetMapping("/review")
    @Operation(summary = "实践申请审核提示")
    @PreAuthorize("@ss.hasPermission('system:practice-apply:comapny:review')")
    @Parameter(name = "practiceApplyId", description = "实践申请编号", required = true, example = "1")
    public CommonResult<List<PracticeApplyRejectRespVO>> reviewPracticeApply(@RequestParam("practiceApplyId") Long practiceApplyId)  {
        //根据practiceApplyId 查询是否有驳回记录
        List<PracticeApplyRejectDO> practiceRejectList = practiceApplyService.getPracticeApplyRejectListByPracticeApplyId(practiceApplyId);
        List<PracticeApplyRejectRespVO> practiceApplyRejectRespVOS = PracticeApplyRejectConvert.INSTANCE.convertList(practiceRejectList);
        return  success(practiceApplyRejectRespVOS);
    }

    // 企业端接口 企业端通过学生对实践的申请
    @GetMapping("/company/review/pass")
    @Operation(summary = "通过申请")
    @PreAuthorize("@ss.hasPermission('system:practice-apply:comapny:review')")
    @Parameter(name = "practiceApplyId", description = "实践申请编号", required = true, example = "1")
    public CommonResult<Boolean> reviewPassPractice(@RequestParam("practiceApplyId") Long practiceApplyId)  {
        practiceApplyService.reviewPassPracticeApply(practiceApplyId);
        return success(true);
    }
    //企业端接口 企业端驳回学生对实践的申请
    @PostMapping("/company/review/failure")
    @Operation(summary = "实践申请审核未通过")
    @PreAuthorize("@ss.hasPermission('system:practice-apply:comapny:review')")
    public CommonResult<Boolean> reviewFailurePractice(@Valid @RequestBody PracticeApplyRejectCreateReqVO practiceApplyRejectCreateReqVO)  {
        practiceApplyService.reviewFailurePracticeApply(practiceApplyRejectCreateReqVO);
        return success(true);
    }
    //企业端接口 企业查询对自己创建的实践的学生申请
    @GetMapping("/company/page")
    @Operation(summary = "获得实践申请分页")
    @PreAuthorize("@ss.hasPermission('system:practice-apply:company:page')")
    public CommonResult<PageResult<PracticeApplyRespVO>> companyGetPracticeApply(@Valid PracticeApplyPageReqVO pageVO) {
        // getCompanyId()  --> practiceList() --> practiceIdList() --> applyList()
        Long companyId = adminUserService.getUser(getLoginUserId()).getTenantId();

        PageResult<PracticeApplyDO> pageResult = practiceApplyService.getApplyListWithCompanyId(pageVO, companyId);
        // 根据当前登录用户的userid 查询申请
        pageVO.setUserId(getLoginUserId());
        PageResult<PracticeApplyRespVO> result = PracticeApplyConvert.INSTANCE.convertPage(pageResult);
        // userid --> nickname practiceId --> companyName - practiceName
        for (int i=0;i<result.getList().size();i++) {
            result.getList().get(i).setPracticeName(adminUserService.getUser(getLoginUserId()).getNickname());
            PracticeDO practice = practiceService.getPractice(result.getList().get(i).getPracticeId());
            result.getList().get(i).setPracticeName(tenantService.getTenant(practice.getCompanyId()).getName()
                    + "-" +practice.getName());
        }

        return success(result);
    }

}
