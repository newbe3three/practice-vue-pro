package com.practice.module.system.controller.admin.practiceapply;

import com.practice.module.system.controller.admin.practice.vo.reject.PracticeRejectCreateReqVO;
import com.practice.module.system.controller.admin.practice.vo.reject.PracticeRejectRespVO;
import com.practice.module.system.controller.admin.practiceapply.vo.apply.*;
import com.practice.module.system.controller.admin.practiceapply.vo.reject.PracticeApplyRejectCreateReqVO;
import com.practice.module.system.controller.admin.practiceapply.vo.reject.PracticeApplyRejectRespVO;
import com.practice.module.system.convert.practice.PracticeRejectConvert;
import com.practice.module.system.convert.practiceapply.PracticeApplyRejectConvert;
import com.practice.module.system.dal.dataobject.practice.PracticeRejectDO;
import com.practice.module.system.dal.dataobject.practiceapply.PracticeApplyRejectDO;
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
    @PostMapping("/create")
    @Operation(summary = "创建实践申请")
    @PreAuthorize("@ss.hasPermission('system:practice-apply:create')")
    public CommonResult<Long> createPracticeApply(@Valid @RequestBody PracticeApplyCreateReqVO createReqVO) {
        PracticeApplyDO practiceApply = PracticeApplyConvert.INSTANCE.convert2(createReqVO);
        practiceApply.setUserId(getLoginUserId());
        return success(practiceApplyService.createPracticeApply(practiceApply));
    }
    @GetMapping("/review")
    @Operation(summary = "实践申请审核提示")
    @PreAuthorize("@ss.hasPermission('system:practice-apply:review')")
    @Parameter(name = "practiceApplyId", description = "实践申请编号", required = true, example = "1")
    public CommonResult<List<PracticeApplyRejectRespVO>> reviewPracticeApply(@RequestParam("practiceApplyId") Long practiceApplyId)  {
        //根据practiceApplyId 查询是否有驳回记录
        List<PracticeApplyRejectDO> practiceRejectList = practiceApplyService.getPracticeApplyRejectListByPracticeApplyId(practiceApplyId);
        List<PracticeApplyRejectRespVO> practiceApplyRejectRespVOS = PracticeApplyRejectConvert.INSTANCE.convertList(practiceRejectList);
        return  success(practiceApplyRejectRespVOS);
    }

    @GetMapping("/review/pass")
    @Operation(summary = "通过申请")
    @PreAuthorize("@ss.hasPermission('system:practice-apply:review')")
    @Parameter(name = "practiceApplyId", description = "实践申请编号", required = true, example = "1")
    public CommonResult<Boolean> reviewPassPractice(@RequestParam("practiceApplyId") Long practiceApplyId)  {
        practiceApplyService.reviewPassPracticeApply(practiceApplyId);
        return success(true);
    }

    @PostMapping("/review/failure")
    @Operation(summary = "实践申请审核未通过")
    @PreAuthorize("@ss.hasPermission('system:practice-apply:review')")
    public CommonResult<Boolean> reviewFailurePractice(@Valid @RequestBody PracticeApplyRejectCreateReqVO practiceApplyRejectCreateReqVO)  {
        practiceApplyService.reviewFailurePracticeApply(practiceApplyRejectCreateReqVO);
        return success(true);
    }


    @PutMapping("/apply/update")
    @Operation(summary = "更新实践申请")
    @PreAuthorize("@ss.hasPermission('system:practice-apply:update')")
    public CommonResult<Boolean> updatePracticeApply(@Valid @RequestBody PracticeApplyUpdateReqVO updateReqVO) {
        PracticeApplyDO practiceApply = PracticeApplyConvert.INSTANCE.convert2(updateReqVO);

        practiceApplyService.updatePracticeApply(practiceApply);
        return success(true);
    }

}
