package com.practice.module.system.controller.admin.practiceschool;

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

@Tag(name = "管理后台 - 学校申请实践")
@RestController
@RequestMapping("/system/practice-school")
@Validated
public class PracticeSchoolController {

    @Resource
    private PracticeSchoolService practiceSchoolService;

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
    @Operation(summary = "获得学校申请实践")
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



    @PostMapping("/apply")
    @Operation(summary = "学校发起实践申请")
    @PreAuthorize("@ss.hasPermission('system:practice-school:apply')")
    public CommonResult<Long> applyPracticeSchool(@Valid @RequestBody PracticeSchoolCreateReqVO createReqVO) {
        return success(practiceSchoolService.applyPracticeSchool(createReqVO));
    }

    @GetMapping("/review/pass")
    @Operation(summary = "企业选定学校")
    @PreAuthorize("@ss.hasPermission('system:practice-apply:review')")
    @Parameter(name = "practiceApplyId", description = "学校申请编号", required = true, example = "1")
    public CommonResult<Boolean> passPracticeSchool(@RequestParam("practiceSchoolId") Long practiceSchoolId)  {
        practiceSchoolService.reviewPassPracticeApply(practiceSchoolId);
        return success(true);
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
