package com.practice.module.system.controller.admin.practiceschool;

import com.practice.module.system.dal.dataobject.practice.PracticeDO;
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

import com.practice.module.system.controller.admin.practiceschool.vo.*;
import com.practice.module.system.dal.dataobject.practiceschool.PracticeSchoolDO;
import com.practice.module.system.convert.practiceschool.PracticeSchoolConvert;
import com.practice.module.system.service.practiceschool.PracticeSchoolService;
import static com.practice.framework.security.core.util.SecurityFrameworkUtils.getLoginUserId;

@Tag(name = "管理后台 - 学校申请实践")
@RestController
@RequestMapping("/system/practice-school")
@Validated
public class PracticeSchoolController {

    @Resource
    private PracticeSchoolService practiceSchoolService;
    @Resource
    private TenantService tenantService;
    @Resource
    private AdminUserService adminUserService;
    @Resource
    private PracticeService practiceService;

    @PostMapping("/create")
    @Operation(summary = "创建学校申请实践")
    @PreAuthorize("@ss.hasPermission('system:practice-school:create')")
    public CommonResult<Long> createPracticeSchool(@Valid @RequestBody PracticeSchoolCreateReqVO createReqVO) {
        return success(practiceSchoolService.createPracticeSchool(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新学校申请实践")
    @PreAuthorize("@ss.hasPermission('system:practice-school:update')")
    public CommonResult<Boolean> updatePracticeSchool(@Valid @RequestBody PracticeSchoolUpdateReqVO updateReqVO) {
        practiceSchoolService.updatePracticeSchool(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除学校申请实践")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('system:practice-school:delete')")
    public CommonResult<Boolean> deletePracticeSchool(@RequestParam("id") Long id) {
        practiceSchoolService.deletePracticeSchool(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "查询学校申请实践的信息")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('system:practice-school:query')")
    public CommonResult<PracticeSchoolRespVO> getPracticeSchool(@RequestParam("id") Long id) {
        PracticeSchoolDO practiceSchool = practiceSchoolService.getPracticeSchool(id);
        return success(PracticeSchoolConvert.INSTANCE.convert(practiceSchool));
    }

    @GetMapping("/list")
    @Operation(summary = "获得学校申请实践列表")
    @Parameter(name = "ids", description = "编号列表", required = true, example = "1024,2048")
    @PreAuthorize("@ss.hasPermission('system:practice-school:query')")
    public CommonResult<List<PracticeSchoolRespVO>> getPracticeSchoolList(@RequestParam("ids") Collection<Long> ids) {
        List<PracticeSchoolDO> list = practiceSchoolService.getPracticeSchoolList(ids);
        return success(PracticeSchoolConvert.INSTANCE.convertList(list));
    }

    @GetMapping("/page")
    @Operation(summary = "获得学校申请实践分页")
    @PreAuthorize("@ss.hasPermission('system:practice-school:query')")
    public CommonResult<PageResult<PracticeSchoolRespVO>> getPracticeSchoolPage(@Valid PracticeSchoolPageReqVO pageVO) {
        PageResult<PracticeSchoolDO> pageResult = practiceSchoolService.getPracticeSchoolPage(pageVO);
        return success(PracticeSchoolConvert.INSTANCE.convertPage(pageResult));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出学校申请实践 Excel")
    @PreAuthorize("@ss.hasPermission('system:practice-school:export')")
    @OperateLog(type = EXPORT)
    public void exportPracticeSchoolExcel(@Valid PracticeSchoolExportReqVO exportReqVO,
              HttpServletResponse response) throws IOException {
        List<PracticeSchoolDO> list = practiceSchoolService.getPracticeSchoolList(exportReqVO);
        // 导出 Excel
        List<PracticeSchoolExcelVO> datas = PracticeSchoolConvert.INSTANCE.convertList02(list);
        ExcelUtils.write(response, "学校申请实践.xls", "数据", PracticeSchoolExcelVO.class, datas);
    }


    //学校端接口 院校端发起对实践的申请
    @PostMapping("/school/create")
    @Operation(summary = "学校发起实践申请")
    @PreAuthorize("@ss.hasPermission('system:practice-school:school:apply')")
    public CommonResult<Long> applyPracticeSchool(@Valid @RequestBody PracticeSchoolCreateReqVO createReqVO) {
        return success(practiceSchoolService.applyPracticeSchool(createReqVO));
    }
    //学校端接口 院校端查询本校发起的对实践的申请
    @GetMapping("/school/page")
    @Operation(summary = "院校端查询本校发起的对实践的申请")
    @PreAuthorize("@ss.hasPermission('system:practice-school:school:page')")
    public CommonResult<PageResult<PracticeSchoolRespVO>> schoolGetPracticeSchoolPage(@Valid PracticeSchoolPageReqVO pageVO) {
        //查询当前用户所属学校发起的申请 user.getTenantId == SchoolId
        pageVO.setSchoolId(adminUserService.getUser(getLoginUserId()).getTenantId());
        PageResult<PracticeSchoolDO> pageResult = practiceSchoolService.getPracticeSchoolPage(pageVO);
        PageResult<PracticeSchoolRespVO> result = PracticeSchoolConvert.INSTANCE.convertPage(pageResult);
        for (int i=0;i<result.getList().size();i++) {
            result.getList().get(i).setSchoolName(tenantService.getTenant(result.getList().get(i).getSchoolId()).getName());
            // practiceName == 企业名+实践职位名
            result.getList().get(i).setPracticeName(tenantService.getTenant(practiceService.getPractice(result.getList().get(i).getPracticeId()).getCompanyId()).getName()
                  + "-" + practiceService.getPractice(result.getList().get(i).getPracticeId()).getName());
        }
        return success(result);
    }

    //企业端接口 企业端通过学校对实践的申请
    @GetMapping("/company/review/pass")
    @Operation(summary = "企业通过学校的申请")
    @PreAuthorize("@ss.hasPermission('system:practice-apply:review')")
    @Parameter(name = "practiceApplyId", description = "学校申请编号", required = true, example = "1")
    public CommonResult<Boolean> passPracticeSchool(@RequestParam("practiceSchoolId") Long practiceSchoolId)  {
        practiceSchoolService.reviewPassPracticeSchoolApply(practiceSchoolId);
        return success(true);
    }
    //企业端接口 企业端查询对自己实践的院校申请
    @GetMapping("/company/page")
    @Operation(summary = "企业端查询对自己实践的院校申请")
    @PreAuthorize("@ss.hasPermission('system:practice-school:school:page')")
    public CommonResult<PageResult<PracticeSchoolRespVO>> companyGetPracticeSchoolPage(@Valid PracticeSchoolPageReqVO pageVO) {
        // 查询当前企业创建的已通过审核的实践 status=1 的实践
        //List<PracticeDO> passPracticeWithCompanyId = practiceService.getPassPracticeWithCompanyId(adminUserService.getUser(getLoginUserId()).getTenantId());
        // 根据上面获得的practiceId 查询对应的学校的申请

        PageResult<PracticeSchoolDO> pageResult = practiceSchoolService.getSchoolApplyWithCompanyId(pageVO, adminUserService.getUser(getLoginUserId()).getTenantId());
        PageResult<PracticeSchoolRespVO> result = PracticeSchoolConvert.INSTANCE.convertPage(pageResult);
        for (int i=0;i<result.getList().size();i++) {
            // practiceName == 企业名+实践职位名
            result.getList().get(i).setPracticeName(tenantService.getTenant(practiceService.getPractice(result.getList().get(i).getPracticeId()).getCompanyId()).getName()
                    + "-" + practiceService.getPractice(result.getList().get(i).getPracticeId()).getName());
            if (result.getList().get(i).getSchoolId() == 0) {
                continue;
            }
            result.getList().get(i).setSchoolName(tenantService.getTenant(result.getList().get(i).getSchoolId()).getName());

        }
        return success(result);
    }



    @GetMapping("/page/practice")
    @Operation(summary = "根据实践id查询数据并分页")
    @PreAuthorize("@ss.hasPermission('system:practice-school:query')")
    public CommonResult<PageResult<PracticeSchoolRespVO>> getPracticeSchoolBypracticeIdPage(@Valid PracticeSchoolPageReqVO pageVO) {
        PracticeSchoolPageReqVO pageQuery = new PracticeSchoolPageReqVO();
        //分页参数 只能有practiceId
        pageQuery.setPracticeId(pageVO.getPracticeId());
        pageQuery.setPageNo(pageVO.getPageNo());
        pageQuery.setPageSize(pageVO.getPageSize());
        PageResult<PracticeSchoolDO> pageResult = practiceSchoolService.getPracticeSchoolPage(pageQuery);
        return success(PracticeSchoolConvert.INSTANCE.convertPage(pageResult));
    }





    @GetMapping("/page/school")
    @Operation(summary = "根据学校id查询数据并分页")
    @PreAuthorize("@ss.hasPermission('system:practice-school:query')")
    public CommonResult<PageResult<PracticeSchoolRespVO>> getPracticeSchoolBySchoolIdPage(@Valid PracticeSchoolPageReqVO pageVO) {
        PracticeSchoolPageReqVO pageQuery = new PracticeSchoolPageReqVO();
        //分页参数 只能有schoolId
        pageQuery.setSchoolId(pageVO.getSchoolId());
        pageQuery.setPageNo(pageVO.getPageNo());
        pageQuery.setPageSize(pageVO.getPageSize());
        PageResult<PracticeSchoolDO> pageResult = practiceSchoolService.getPracticeSchoolPage(pageQuery);

        return success(PracticeSchoolConvert.INSTANCE.convertPage(pageResult));
    }




    @GetMapping("/page/status")
    @Operation(summary = "根据status查询数据并分页")
    @PreAuthorize("@ss.hasPermission('system:practice-school:query')")
    public CommonResult<PageResult<PracticeSchoolRespVO>> getPracticeSchoolByStatus(@Valid PracticeSchoolPageReqVO pageVO) {
        PracticeSchoolPageReqVO pageQuery = new PracticeSchoolPageReqVO();
        //分页参数 只能有practiceId
        pageQuery.setStatus(pageVO.getStatus());
        pageQuery.setPageNo(pageVO.getPageNo());
        pageQuery.setPageSize(pageVO.getPageSize());
        PageResult<PracticeSchoolDO> pageResult = practiceSchoolService.getPracticeSchoolPage(pageQuery);
        return success(PracticeSchoolConvert.INSTANCE.convertPage(pageResult));
    }
}
