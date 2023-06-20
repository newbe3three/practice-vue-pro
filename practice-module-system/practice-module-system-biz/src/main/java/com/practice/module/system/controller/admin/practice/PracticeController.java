package com.practice.module.system.controller.admin.practice;

import com.practice.module.system.controller.admin.practice.vo.practice.*;
import com.practice.module.system.controller.admin.practice.vo.reject.PracticeRejectCreateReqVO;
import com.practice.module.system.controller.admin.practice.vo.reject.PracticeRejectRespVO;
import com.practice.module.system.controller.admin.resourcearticle.vo.reject.ResourceArticleRejectCreateReqVO;
import com.practice.module.system.convert.practice.PracticeRejectConvert;
import com.practice.module.system.dal.dataobject.practice.PracticeRejectDO;
import com.practice.module.system.service.practice.PracticeRejectService;
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

@Tag(name = "管理后台 - 实践")
@RestController
@RequestMapping("/system/practice")
@Validated
public class PracticeController {

    @Resource
    private PracticeService practiceService;
    @Resource
    private PracticeRejectService practiceRejectService;

    @PostMapping("/create")
    @Operation(summary = "创建实践")
    @PreAuthorize("@ss.hasPermission('system:practice:create')")
    public CommonResult<Long> createPractice(@Valid @RequestBody PracticeCreateReqVO createReqVO) {
        return success(practiceService.createPractice(createReqVO));
    }

    @PutMapping("/update/apply")
    @Operation(summary = "驳回后修改申请")
    @PreAuthorize("@ss.hasPermission('system:practice:update')")
    public CommonResult<Boolean> updatePractice(@Valid @RequestBody PracticeUpdateReqVO updateReqVO) {
        practiceService.updatePracticeApply(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除实践")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('system:practice:delete')")
    public CommonResult<Boolean> deletePractice(@RequestParam("id") Long id) {
        practiceService.deletePractice(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得实践")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('system:practice:query')")
    public CommonResult<PracticeRespVO> getPractice(@RequestParam("id") Long id) {
        PracticeDO practice = practiceService.getPractice(id);
        return success(PracticeConvert.INSTANCE.convert(practice));
    }

    @GetMapping("/list")
    @Operation(summary = "获得实践列表")
    @Parameter(name = "ids", description = "编号列表", required = true, example = "1024,2048")
    @PreAuthorize("@ss.hasPermission('system:practice:query')")
    public CommonResult<List<PracticeRespVO>> getPracticeList(@RequestParam("ids") Collection<Long> ids) {
        List<PracticeDO> list = practiceService.getPracticeList(ids);
        return success(PracticeConvert.INSTANCE.convertList(list));
    }

    @GetMapping("/page")
    @Operation(summary = "获得实践分页")
    @PreAuthorize("@ss.hasPermission('system:practice:query')")
    public CommonResult<PageResult<PracticeRespVO>> getPracticePage(@Valid PracticePageReqVO pageVO) {
        PageResult<PracticeDO> pageResult = practiceService.getPracticePage(pageVO);
        return success(PracticeConvert.INSTANCE.convertPage(pageResult));
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


    @GetMapping("/review/pass")
    @Operation(summary = "实践审核通过")
    @PreAuthorize("@ss.hasPermission('system:practice:review')")
    public CommonResult<Boolean> reviewPassPractice(@RequestParam("practiceId") Long practiceId)  {
        practiceService.reviewPassPractice(practiceId);
        return success(true);
    }
    @PostMapping("/review/failure")
    @Operation(summary = "实践审核未通过")
    @PreAuthorize("@ss.hasPermission('system:practice:review')")
    public CommonResult<Boolean> reviewFailurePractice(@Valid @RequestBody PracticeRejectCreateReqVO rejectCreateReqVO)  {
        practiceService.reviewFailurePractice(rejectCreateReqVO);
        return success(true);
    }


    @GetMapping("/query/pass")
    @Operation(summary = "查询审核通过的实践")
    @PreAuthorize("@ss.hasPermission('system:practice:query')")
    public CommonResult<PageResult<PracticeRespVO>> queryPassPractice(@Valid PracticePageReqVO pageVO)  {
        PracticePageReqVO page = new PracticePageReqVO();
        page.setPageNo(pageVO.getPageNo());
        page.setPageSize(pageVO.getPageSize());
        page.setStatus((byte) 1);
        PageResult<PracticeDO> pageResult = practiceService.getPracticePage(page);

        return success(PracticeConvert.INSTANCE.convertPage(pageResult));
    }


}
