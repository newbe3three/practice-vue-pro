package com.practice.module.system.controller.admin.practice;

import com.practice.module.system.controller.admin.practice.vo.practice.*;
import com.practice.module.system.controller.admin.practice.vo.reject.PracticeRejectCreateReqVO;
import com.practice.module.system.controller.admin.practice.vo.reject.PracticeRejectRespVO;
import com.practice.module.system.controller.admin.resourcearticle.vo.reject.ResourceArticleRejectCreateReqVO;
import com.practice.module.system.convert.practice.PracticeRejectConvert;
import com.practice.module.system.dal.dataobject.practice.PracticeRejectDO;
import com.practice.module.system.service.practice.PracticeRejectService;
import com.practice.module.system.service.practiceapply.PracticeApplyService;
import com.practice.module.system.service.practiceschool.PracticeSchoolService;
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

import com.practice.module.system.dal.dataobject.practice.PracticeDO;
import com.practice.module.system.convert.practice.PracticeConvert;
import com.practice.module.system.service.practice.PracticeService;
import static com.practice.framework.security.core.util.SecurityFrameworkUtils.getLoginUserId;

@Tag(name = "管理后台 - 实践")
@RestController
@RequestMapping("/system/practice")
@Validated
public class PracticeController {

    @Resource
    private PracticeService practiceService;
    @Resource
    private PracticeRejectService practiceRejectService;
    @Resource
    private TenantService tenantService;
    @Resource
    private AdminUserService adminUserService;
    @Resource
    private PracticeApplyService practiceApplyService;


    @GetMapping("/list")
    @Operation(summary = "获得实践列表")
    @Parameter(name = "ids", description = "编号列表", required = true, example = "1024,2048")
    @PreAuthorize("@ss.hasPermission('system:practice:query')")
    public CommonResult<List<PracticeRespVO>> getPracticeList(@RequestParam("ids") Collection<Long> ids) {
        List<PracticeDO> list = practiceService.getPracticeList(ids);
        List<PracticeRespVO> practiceRespVOS = PracticeConvert.INSTANCE.convertList(list);
        for(int i=0;i<=practiceRespVOS.size();i++) {
            practiceRespVOS.get(i).setCompanyName(tenantService.getTenant(list.get(i).getCompanyId()).getName());
        }

        return success(practiceRespVOS);
    }


    @GetMapping("/export-excel")
    @Operation(summary = "导出实践 Excel")
    @PreAuthorize("@ss.hasPermission('system:practice:export')")
    @OperateLog(type = EXPORT)
    public void exportPracticeExcel(@Valid PracticeExportReqVO exportReqVO,
              HttpServletResponse response) throws IOException {
        List<PracticeDO> list = practiceService.getPracticeList(exportReqVO);
        // 导出 Excel
        List<PracticeExcelVO> datas = PracticeConvert.INSTANCE.convertList02(list);
        ExcelUtils.write(response, "实践.xls", "数据", PracticeExcelVO.class, datas);
    }

    @GetMapping("/get")
    @Operation(summary = "查询实践的信息")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('system:practice:get')")
    public CommonResult<PracticeRespVO> getPractice(@RequestParam("id") Long id) {
        PracticeDO practice = practiceService.getPractice(id);
        PracticeRespVO convert = PracticeConvert.INSTANCE.convert(practice);
        // companyId --> tenantName
        convert.setCompanyName(tenantService.getTenant(practice.getCompanyId()).getName());
        convert.setAddress(tenantService.getTenant(practice.getCompanyId()).getAddress());
        return success(convert);
    }


    //平台端接口 实践审核
    @GetMapping("/review")
    @Operation(summary = "实践审核")
    @PreAuthorize("@ss.hasPermission('system:practice:review')")
    @Parameter(name = "practiceId", description = "实践编号", required = true, example = "1")
    public CommonResult<List<PracticeRejectRespVO>> reviewPractice(@RequestParam("practiceId") Long practiceId)  {
        //根据practiceId查询审核驳回记录 如果有就返回practiceRejectList 如果没有就返回null，前端就没有可以显示的数据
        List<PracticeRejectDO> practiceRejectList = practiceRejectService.getPracticeRejectListByArticleId(practiceId);
        List<PracticeRejectRespVO> practiceRejectRespVOS = PracticeRejectConvert.INSTANCE.convertList(practiceRejectList);
        return  success(practiceRejectRespVOS);
    }

    //平台端接口 实践审核通过
    @GetMapping("/review/pass")
    @Operation(summary = "实践审核通过")
    @PreAuthorize("@ss.hasPermission('system:practice:review')")
    public CommonResult<Boolean> reviewPassPractice(@RequestParam("practiceId") Long practiceId)  {
        practiceService.reviewPassPractice(practiceId);
        return success(true);
    }
    //平台端接口 实践审核未通过
    @PostMapping("/review/failure")
    @Operation(summary = "实践审核未通过")
    @PreAuthorize("@ss.hasPermission('system:practice:review')")
    public CommonResult<Boolean> reviewFailurePractice(@Valid @RequestBody PracticeRejectCreateReqVO rejectCreateReqVO)  {
        practiceService.reviewFailurePractice(rejectCreateReqVO);
        return success(true);
    }
    //平台端接口 查询实践数据分页
    @GetMapping("/page")
    @Operation(summary = "获得实践分页")
    @PreAuthorize("@ss.hasPermission('system:practice:query:page')")
    public CommonResult<PageResult<PracticeRespVO>> getPracticePage(@Valid PracticePageReqVO pageVO) {
        PageResult<PracticeDO> pageResult = practiceService.getPracticePage(pageVO);
        PageResult<PracticeRespVO> result = PracticeConvert.INSTANCE.convertPage2(pageResult);
        System.out.println("111111111111111======"+ result.getList().size());

        for (int i=0;i<result.getList().size();i++) {
            result.getList().get(i).setCompanyName(tenantService.getTenant(result.getList().get(i).getCompanyId()).getName());
        }
        return success(result);
    }



    //学校端接口 院校端查询通过审核但还未确定学校的实践
    @GetMapping("/school/page")
    @Operation(summary = "院校端查询审核通过还未确定学校的实践")
    @PreAuthorize("@ss.hasPermission('system:practice:school:query')")
    public CommonResult<PageResult<PracticeRespVO>> schoolQueryPractice(@Valid PracticePageReqVO pageVO)  {
        //可以查询的实践的状态为 1的实践（通过审核实践，并且没选定学校的实践）
        PracticePageReqVO page = new PracticePageReqVO();
        page.setPageNo(pageVO.getPageNo());
        page.setPageSize(pageVO.getPageSize());
        page.setStatus((byte) 1);
        PageResult<PracticeDO> pageResult = practiceService.getPracticePage(page);
        PageResult<PracticeRespVO> result = PracticeConvert.INSTANCE.convertPage(pageResult);
        for (int i=0;i<result.getList().size();i++) {
            result.getList().get(i).setCompanyName(tenantService.getTenant(pageResult.getList().get(i).getCompanyId()).getName());

        }
        return success(result);
    }

    //学生端接口 学生查询可以申请的实践
    @GetMapping("/student/page")
    @Operation(summary = "学生端查询可以申请的实践")
    @PreAuthorize("@ss.hasPermission('system:practice:student:query')")
    public CommonResult<PageResult<PracticeRespVO>> studentQueryPractice(@Valid PracticePageReqVO pageVO)  {
        //可以查询本schoolId下的实践，已经schoolId为0的实践
        //根据本校的schoolId查询
        //List<Long> practiceIdList = practiceSchoolService.getPracticeIdListWithSchoolId(adminUserService.getUser(getLoginUserId()).getTenantId());
        //查询schoolId为0的实践
        //List<Long> listAll = practiceSchoolService.getPracticeIdListWithSchoolId(0L);
        //practiceIdList.addAll(listAll);
        //pageVO.setPracticeIdList(practiceIdList);
        PageResult<PracticeDO> pageResult = practiceApplyService.studentGetPracticePage(pageVO,adminUserService.getUser(getLoginUserId()).getTenantId());

        PageResult<PracticeRespVO> result = PracticeConvert.INSTANCE.convertPage(pageResult);
        for (int i=0;i<result.getList().size();i++) {
            result.getList().get(i).setCompanyName(tenantService.getTenant(pageResult.getList().get(i).getCompanyId()).getName());

        }
        return success(result);
    }



    //企业端接口 发起实践的创建
    @PostMapping("/company/create")
    @Operation(summary = "创建实践")
    @PreAuthorize("@ss.hasPermission('system:practice:company:create')")
    public CommonResult<Long> companyCreatePractice(@Valid @RequestBody PracticeCreateReqVO createReqVO) {
        //用户只能向自己所属的企业创建实践 tenantId == companyId
        createReqVO.setCompanyId(adminUserService.getUser(getLoginUserId()).getTenantId());
        return success(practiceService.createPractice(createReqVO));
    }

    //企业端接口，实践创建申请驳回后，修改申请
    @PutMapping("/company/update")
    @Operation(summary = "驳回后修改申请")
    @PreAuthorize("@ss.hasPermission('system:practice:company:update')")
    public CommonResult<Boolean> companyUpdatePractice(@Valid @RequestBody PracticeUpdateReqVO updateReqVO) {
        practiceService.updatePracticeApply(updateReqVO,adminUserService.getUser(getLoginUserId()).getTenantId());
        return success(true);
    }

    //企业端的接口，查询自己所属企业的所发布的实践分页（根据tenantId == companyID）
    @GetMapping("/company/page")
    @Operation(summary = "获得本企业的实践分页")
    @PreAuthorize("@ss.hasPermission('system:practice:company:query')")
    public CommonResult<PageResult<PracticeRespVO>> companyGetPracticePage(@Valid PracticePageReqVO pageVO) {
        //根据当前用户的tenantId来限制可以查询到的范围只能是自己所属企业的实践
        pageVO.setCompanyId(adminUserService.getUser(getLoginUserId()).getTenantId());
        System.out.println("nihao"+adminUserService.getUser(getLoginUserId()).getTenantId());
        PageResult<PracticeDO> pageResult = practiceService.getPracticePage(pageVO);
        PageResult<PracticeRespVO> result = PracticeConvert.INSTANCE.convertPage(pageResult);
        for (int i=0;i<result.getList().size();i++) {
            result.getList().get(i).setCompanyName(tenantService.getTenant(pageResult.getList().get(i).getCompanyId()).getName());

        }
        return success(result);
    }
    //企业端接口，企业端创建的实践通过审核后，企业可以选择某些学校也可以不选择，直接发起对实践状态的修改为征集中
    @GetMapping("/company/confirm")
    @Operation(summary = "企业修改实践状态为已经选定学校")
    @PreAuthorize("@ss.hasPermission('system:practice:company:confirm')")
    public CommonResult<Boolean> companyConfirmPractice(@RequestParam("practiceId") Long practiceId) {
        practiceService.confirmPracticeByCompany(practiceId,adminUserService.getUser(getLoginUserId()).getTenantId());
        return success(true);
    }

}
