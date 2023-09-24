package com.practice.module.system.controller.admin.resourcearticle;

import com.practice.module.system.controller.admin.resourcearticle.vo.article.*;
import com.practice.module.system.controller.admin.resourcearticle.vo.reject.ResourceArticleRejectCreateReqVO;
import com.practice.module.system.controller.admin.resourcearticle.vo.reject.ResourceArticleRejectRespVO;
import com.practice.module.system.convert.resourcearticle.ResourceArticleRejectConvert;
import com.practice.module.system.dal.dataobject.resourcearticle.ResourceArticleRejectDO;
import com.practice.module.system.service.resourcearticle.ResourceArticleRejectService;
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

import com.practice.module.system.dal.dataobject.resourcearticle.ResourceArticleDO;
import com.practice.module.system.convert.resourcearticle.ResourceArticleConvert;
import com.practice.module.system.service.resourcearticle.ResourceArticleService;

import static com.practice.framework.security.core.util.SecurityFrameworkUtils.getLoginUserId;

@Tag(name = "管理后台 - 文章资源")
@RestController
@RequestMapping("/system/resource-article")
@Validated
public class ResourceArticleController {

    @Resource
    private ResourceArticleService resourceArticleService;
    @Resource
    private ResourceArticleRejectService resourceArticleRejectService;
    @Resource
    private AdminUserService adminUserService;
    private String userName;

    @PostMapping("/create")
    @Operation(summary = "创建文章资源")
    @PreAuthorize("@ss.hasPermission('system:resource-article:create')")
    public CommonResult<Long> createResourceArticle(@Valid @RequestBody ResourceArticleCreateReqVO createReqVO) {
        //根据当前登录用户id，写入资源发布时用户id
        Long loginUserId = getLoginUserId();
        createReqVO.setUserId(loginUserId);
        //TODO:从数据库中获得文章资源类别编号
        //发布文章资源，设置初始状态为0
        createReqVO.setStatus((byte) 0);
        //发布文章资源时，设置初始浏览量为0；
        createReqVO.setViewCount(0L);

        return success(resourceArticleService.createResourceArticle(createReqVO));
    }



    @DeleteMapping("/delete")
    @Operation(summary = "删除文章资源")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('system:resource-article:delete')")
    public CommonResult<Boolean> deleteResourceArticle(@RequestParam("id") Long id) {
        resourceArticleService.deleteResourceArticle(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得文章资源")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('system:resource-article:query')")
    public CommonResult<ResourceArticleRespVO> getResourceArticle(@RequestParam("id") Long id) {
        ResourceArticleDO resourceArticle = resourceArticleService.getResourceArticle(id);
        ResourceArticleRespVO resourceArticleRespVO = ResourceArticleConvert.INSTANCE.convert(resourceArticle);
        //根据用户id写入对应的username
        userName = adminUserService.getUser(resourceArticleRespVO.getUserId()).getUsername();
        resourceArticleRespVO.setUserName(userName);
        return success(resourceArticleRespVO);
    }

    @GetMapping("/list")
    @Operation(summary = "获得文章资源列表")
    @Parameter(name = "ids", description = "编号列表", required = true, example = "1024,2048")
    @PreAuthorize("@ss.hasPermission('system:resource-article:query')")
    public CommonResult<List<ResourceArticleRespVO>> getResourceArticleList(@RequestParam("ids") Collection<Long> ids) {
        List<ResourceArticleDO> list = resourceArticleService.getResourceArticleList(ids);
        return success(ResourceArticleConvert.INSTANCE.convertList(list));
    }


    @GetMapping("/page")
    @Operation(summary = "获得文章资源分页")
    @PreAuthorize("@ss.hasPermission('system:resource-article:query')")
    public CommonResult<PageResult<ResourceArticleRespVO>> getResourceArticlePage(@Valid ResourceArticlePageReqVO pageVO) {
        PageResult<ResourceArticleDO> pageResult = resourceArticleService.getResourceArticlePage(pageVO);
        PageResult<ResourceArticleRespVO> resourceArticleRespVOPageResult = ResourceArticleConvert.INSTANCE.convertPage(pageResult);
        for (int i=0; i < resourceArticleRespVOPageResult.getList().size();i++) {
            System.out.println("nihao"+resourceArticleRespVOPageResult.getList().get(i).getUserId());
            userName = adminUserService.getUser(resourceArticleRespVOPageResult.getList().get(i).getUserId()).getUsername();
            resourceArticleRespVOPageResult.getList().get(i).setUserName(userName);
        }

        return success(resourceArticleRespVOPageResult);
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出文章资源 Excel")
    @PreAuthorize("@ss.hasPermission('system:resource-article:export')")
    @OperateLog(type = EXPORT)
    public void exportResourceArticleExcel(@Valid ResourceArticleExportReqVO exportReqVO,
              HttpServletResponse response) throws IOException {
        List<ResourceArticleDO> list = resourceArticleService.getResourceArticleList(exportReqVO);
        // 导出 Excel
        List<ResourceArticleExcelVO> datas = ResourceArticleConvert.INSTANCE.convertList02(list);
        ExcelUtils.write(response, "文章资源.xls", "数据", ResourceArticleExcelVO.class, datas);
    }

    @GetMapping("/query/current")
    @Operation(summary = "查询当前用户发布的文章资源列表")
    //@PreAuthorize()
    public CommonResult<PageResult<ResourceArticleRespVO>> getResourceArticleListWithCurrent(@Valid ResourceArticlePageReqVO pageVO) {
        //getLoginUserId 获取当前用户id
        Long loginUserId = getLoginUserId();
        //ResourceArticlePageReqVO pageVO = new ResourceArticlePageReqVO();
        pageVO.setUserId(loginUserId);
        PageResult<ResourceArticleDO> pageResult = resourceArticleService.getResourceArticlePage(pageVO);
        PageResult<ResourceArticleRespVO> resourceArticleRespVOPageResult = ResourceArticleConvert.INSTANCE.convertPage(pageResult);
        for (int i=0; i<resourceArticleRespVOPageResult.getList().size();i++) {
            userName = adminUserService.getUser(resourceArticleRespVOPageResult.getList().get(i).getUserId()).getUsername();
            resourceArticleRespVOPageResult.getList().get(i).setUserName(userName);
        }

        return success(resourceArticleRespVOPageResult);
    }
//    @GetMapping("/query/username")
//    @Operation(summary = "根据用户名称查询文章资源分页")
//    @PreAuthorize("@ss.hasPermission('system:resource-article:query')")
//    @Parameter(name = "username", description = "用户名", required = true, example = "admin")
//    public CommonResult<PageResult<ResourceArticleRespVO>> getResourceArticleListWithUserName(@Valid ResourceArticlePageReqVO pageVO) {
//
//        //ResourceArticlePageReqVO pageVO = new ResourceArticlePageReqVO();
//        //先根据username查询userid，然后再去查询article表
//        pageVO.setUserId(adminUserService.getUserIdWithUserName(pageVO.getUserName()));
//        PageResult<ResourceArticleDO> pageResult = resourceArticleService.getResourceArticlePage(pageVO);
//        PageResult<ResourceArticleRespVO> resourceArticleRespVOPageResult = ResourceArticleConvert.INSTANCE.convertPage(pageResult);
//        for (int i=0; i<resourceArticleRespVOPageResult.getList().size();i++) {
//            userName = adminUserService.getUser(resourceArticleRespVOPageResult.getList().get(i).getUserId()).getUsername();
//            resourceArticleRespVOPageResult.getList().get(i).setUserName(userName);
//        }
//
//        return success(resourceArticleRespVOPageResult);
//    }
//    //Todo:分页？？？
    @GetMapping("/review")
    @Operation(summary = "文章资源审核")
    @PreAuthorize("@ss.hasPermission('system:resource-article:review')")
    @Parameter(name = "articleId", description = "文章编号", required = true, example = "1")
    public CommonResult<List<ResourceArticleRejectRespVO>> reviewResourceArticle(@RequestParam("articleId") Long articleId)  {
        //根据articleId查询审核驳回记录 如果有就返回articleRejctList 如果没有就返回null，前端就没有可以显示的数据
        List<ResourceArticleRejectDO> rejectList = resourceArticleRejectService.getResourceArticleRejectListByArticleId(articleId);
        List<ResourceArticleRejectRespVO> resourceArticleRejectRespVOS = ResourceArticleRejectConvert.INSTANCE.convertList(rejectList);
        return  success(resourceArticleRejectRespVOS);
    }


    @GetMapping("/review/pass")
    @Operation(summary = "文章资源审核通过")
    @PreAuthorize("@ss.hasPermission('system:resource-article:review')")
    @Parameter(name = "id", description = "文章编号", required = true, example = "1")
    public CommonResult<Boolean> reviewPassResourceArticle(@RequestParam("id") Long id)  {
        resourceArticleService.reviewPassResourceArticle(id);
        return success(true);
    }
    @PostMapping("/review/failure")
    @PreAuthorize("@ss.hasPermission('system:resource-article:review')")
    @Operation(summary = "文章资源审核不通过")
    //@Parameter(name = "id", description = "文章编号", required = true, example = "1")
    public CommonResult<Boolean> reviewFailureResourceArticle(@Valid @RequestBody ResourceArticleRejectCreateReqVO rejectCreateReqVO)  {
        resourceArticleService.reviewFailureResourceArticle(rejectCreateReqVO);
        return success(true);
    }

    @GetMapping("/takedown")
    @Operation(summary = "文章资源下架")
    @PreAuthorize("@ss.hasPermission('system:resource-article:down')")
    @Parameter(name = "id", description = "文章编号", required = true, example = "1")
    public CommonResult<Boolean> takeDownResourceArticle(@RequestParam("id") Long id)  {
        resourceArticleService.takeDownResourceArticle(id);
        return success(true);
    }

//    @GetMapping("/query/category")
//    @Operation(summary = "根据类别编号查询文章资源并分页")
//    @PreAuthorize("@ss.hasPermission('system:resource-article:query')")
//   // @Parameter(name = "categoryId", description = "类别编号", required = true, example = "1")
//    public CommonResult<PageResult<ResourceArticleRespVO>> getResourceArticleListWithCategoryId(@Valid ResourceArticlePageReqVO pageReqVO) {
//      //  类别编号不存在就查询不出数据
//
//        PageResult<ResourceArticleDO> pageResult = resourceArticleService.getResourceArticlePage(pageReqVO);
//        PageResult<ResourceArticleRespVO> resourceArticleRespVOPageResult = ResourceArticleConvert.INSTANCE.convertPage(pageResult);
//        for (int i=0; i<resourceArticleRespVOPageResult.getList().size();i++) {
//            userName = adminUserService.getUser(resourceArticleRespVOPageResult.getList().get(i).getUserId()).getUsername();
//            resourceArticleRespVOPageResult.getList().get(i).setUserName(userName);
//        }
//
//        return success(resourceArticleRespVOPageResult);
//    }


//    @PutMapping("/update")
//    @Operation(summary = "文章资源修改")
//    @PreAuthorize("@ss.hasPermission('system:resource-article:update')")
//    public CommonResult<Boolean> updateResourceArticle(@Valid @RequestBody ResourceArticleUpdateReqVO updateReqVO) {
//        resourceArticleService.updateResourceArticle(updateReqVO);
//        return success(true);
//    }
}


