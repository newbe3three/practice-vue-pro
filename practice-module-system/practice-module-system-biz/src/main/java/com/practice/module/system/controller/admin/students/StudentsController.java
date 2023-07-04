package com.practice.module.system.controller.admin.students;

import com.practice.module.system.dal.dataobject.user.AdminUserDO;
import com.practice.module.system.service.dept.DeptService;
import com.practice.module.system.service.sms.SmsSendService;
import com.practice.module.system.service.teachers.TeachersService;
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

import com.practice.module.system.controller.admin.students.vo.*;
import com.practice.module.system.dal.dataobject.students.StudentsDO;
import com.practice.module.system.convert.students.StudentsConvert;
import com.practice.module.system.service.students.StudentsService;

@Tag(name = "管理后台 - 学生信息")
@RestController
@RequestMapping("/system/students")
@Validated
public class StudentsController {

    @Resource
    private StudentsService studentsService;
    @Resource
    private DeptService deptService;
    @Resource
    private TeachersService teachersService;
    @Resource
    private SmsSendService smsSendService;
    @Resource
    private AdminUserService adminUserService;

    @PostMapping("/create")
    @Operation(summary = "创建学生信息")
    @PreAuthorize("@ss.hasPermission('system:students:create')")
    public CommonResult<Long> createStudents(@Valid @RequestBody StudentsCreateReqVO createReqVO) {
        return success(studentsService.createStudents(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新学生信息")
    @PreAuthorize("@ss.hasPermission('system:students:update')")
    public CommonResult<Boolean> updateStudents(@Valid @RequestBody StudentsUpdateReqVO updateReqVO) {
        studentsService.updateStudents(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除学生信息")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('system:students:delete')")
    public CommonResult<Boolean> deleteStudents(@RequestParam("id") Long id) {
        studentsService.deleteStudents(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得学生信息")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('system:students:query')")
    public CommonResult<StudentsRespVO> getStudents(@RequestParam("id") Long id) {
        StudentsDO students = studentsService.getStudents(id);
        return success(StudentsConvert.INSTANCE.convert(students));
    }

    @GetMapping("/list")
    @Operation(summary = "获得学生信息列表")
    @Parameter(name = "ids", description = "编号列表", required = true, example = "1024,2048")
    @PreAuthorize("@ss.hasPermission('system:students:query')")
    public CommonResult<List<StudentsRespVO>> getStudentsList(@RequestParam("ids") Collection<Long> ids) {
        List<StudentsDO> list = studentsService.getStudentsList(ids);
        return success(StudentsConvert.INSTANCE.convertList(list));
    }

    @GetMapping("/page")
    @Operation(summary = "获得学生信息分页")
    @PreAuthorize("@ss.hasPermission('system:students:query')")
    public CommonResult<PageResult<StudentsRespVO>> getStudentsPage(@Valid StudentsPageReqVO pageVO) {
        PageResult<StudentsDO> pageResult = studentsService.getStudentsPage(pageVO);
        return success(StudentsConvert.INSTANCE.convertPage(pageResult));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出学生信息 Excel")
    @PreAuthorize("@ss.hasPermission('system:students:export')")
    @OperateLog(type = EXPORT)
    public void exportStudentsExcel(@Valid StudentsExportReqVO exportReqVO,
              HttpServletResponse response) throws IOException {
        List<StudentsDO> list = studentsService.getStudentsList(exportReqVO);
        // 导出 Excel
        List<StudentsExcelVO> datas = StudentsConvert.INSTANCE.convertList02(list);
        ExcelUtils.write(response, "学生信息.xls", "数据", StudentsExcelVO.class, datas);
    }

    @GetMapping("/query")
    @Operation(summary = "获取学生信息根据userid")
    @Parameter(name = "userId", description = "用户编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('system:students:query')")
    public CommonResult<StudentRespVO> getStudentsWithUserId(@RequestParam("userId") Long userId) {
        StudentsDO student = studentsService.getStudentWithUserId(userId);
        StudentRespVO studentRespVO = StudentsConvert.INSTANCE.convert2(student);
        studentRespVO.setDeptName( deptService.getDept(student.getDeptId()).getName());
        studentRespVO.setTeacherName(teachersService.getTeachersWithUserId(student.getTeacherId()).getName());
        return success(studentRespVO);
    }



}


