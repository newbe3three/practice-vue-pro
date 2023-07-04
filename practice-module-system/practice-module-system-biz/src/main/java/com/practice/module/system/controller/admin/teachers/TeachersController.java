package com.practice.module.system.controller.admin.teachers;

import com.practice.module.system.controller.admin.students.vo.StudentRespVO;
import com.practice.module.system.convert.students.StudentsConvert;
import com.practice.module.system.dal.dataobject.students.StudentsDO;
import com.practice.module.system.service.dept.DeptService;
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

import com.practice.module.system.controller.admin.teachers.vo.*;
import com.practice.module.system.dal.dataobject.teachers.TeachersDO;
import com.practice.module.system.convert.teachers.TeachersConvert;
import com.practice.module.system.service.teachers.TeachersService;

@Tag(name = "管理后台 - 导师信息")
@RestController
@RequestMapping("/system/teachers")
@Validated
public class TeachersController {

    @Resource
    private TeachersService teachersService;
    @Resource
    private DeptService deptService;
    @PostMapping("/create")
    @Operation(summary = "创建导师信息")
    @PreAuthorize("@ss.hasPermission('system:teachers:create')")
    public CommonResult<Long> createTeachers(@Valid @RequestBody TeachersCreateReqVO createReqVO) {
        return success(teachersService.createTeachers(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新导师信息")
    @PreAuthorize("@ss.hasPermission('system:teachers:update')")
    public CommonResult<Boolean> updateTeachers(@Valid @RequestBody TeachersUpdateReqVO updateReqVO) {
        teachersService.updateTeachers(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除导师信息")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('system:teachers:delete')")
    public CommonResult<Boolean> deleteTeachers(@RequestParam("id") Long id) {
        teachersService.deleteTeachers(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得导师信息")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('system:teachers:query')")
    public CommonResult<TeachersRespVO> getTeachers(@RequestParam("id") Long id) {
        TeachersDO teachers = teachersService.getTeachers(id);
        return success(TeachersConvert.INSTANCE.convert(teachers));
    }

    @GetMapping("/list")
    @Operation(summary = "获得导师信息列表")
    @Parameter(name = "ids", description = "编号列表", required = true, example = "1024,2048")
    @PreAuthorize("@ss.hasPermission('system:teachers:query')")
    public CommonResult<List<TeachersRespVO>> getTeachersList(@RequestParam("ids") Collection<Long> ids) {
        List<TeachersDO> list = teachersService.getTeachersList(ids);
        return success(TeachersConvert.INSTANCE.convertList(list));
    }

    @GetMapping("/page")
    @Operation(summary = "获得导师信息分页")
    @PreAuthorize("@ss.hasPermission('system:teachers:query')")
    public CommonResult<PageResult<TeachersRespVO>> getTeachersPage(@Valid TeachersPageReqVO pageVO) {
        PageResult<TeachersDO> pageResult = teachersService.getTeachersPage(pageVO);
        return success(TeachersConvert.INSTANCE.convertPage(pageResult));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出导师信息 Excel")
    @PreAuthorize("@ss.hasPermission('system:teachers:export')")
    @OperateLog(type = EXPORT)
    public void exportTeachersExcel(@Valid TeachersExportReqVO exportReqVO,
              HttpServletResponse response) throws IOException {
        List<TeachersDO> list = teachersService.getTeachersList(exportReqVO);
        // 导出 Excel
        List<TeachersExcelVO> datas = TeachersConvert.INSTANCE.convertList02(list);
        ExcelUtils.write(response, "导师信息.xls", "数据", TeachersExcelVO.class, datas);
    }


    @GetMapping("/query")
    @Operation(summary = "获取教师信息根据userid")
    @Parameter(name = "userId", description = "用户编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('system:teachers:query')")
    public CommonResult<TeacherRespVO> getStudentsWithUserId(@RequestParam("userId") Long userId) {
        TeachersDO teacher = teachersService.getTeachersWithUserId(userId);
        TeacherRespVO teacherRespVO = TeachersConvert.INSTANCE.convert2(teacher);
        teacherRespVO.setDeptName(deptService.getDept(teacher.getDeptId()).getName());
        return success(teacherRespVO);
    }
}
