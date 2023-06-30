package com.practice.module.system.controller.admin.permission;

import cn.hutool.core.collection.CollUtil;
import com.practice.framework.common.pojo.CommonResult;
import com.practice.module.system.controller.admin.permission.vo.permission.PermissionAssignRoleDataScopeReqVO;
import com.practice.module.system.controller.admin.permission.vo.permission.PermissionAssignRoleMenuReqVO;
import com.practice.module.system.controller.admin.permission.vo.permission.PermissionAssignUserRoleReqVO;
import com.practice.module.system.controller.admin.students.vo.StudentCreateVO;
import com.practice.module.system.controller.admin.students.vo.StudentsCreateReqVO;
import com.practice.module.system.controller.admin.teachers.vo.TeacherCreateVO;
import com.practice.module.system.controller.admin.teachers.vo.TeachersCreateReqVO;
import com.practice.module.system.service.permission.PermissionService;
import com.practice.module.system.service.permission.RoleService;
import com.practice.module.system.service.students.StudentsService;
import com.practice.module.system.service.teachers.TeachersService;
import com.practice.module.system.service.tenant.TenantService;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.HashSet;
import java.util.Set;

import static com.practice.framework.common.pojo.CommonResult.success;

/**
 * 权限 Controller，提供赋予用户、角色的权限的 API 接口
 *
 * @author 芋道源码
 */
@Tag(name = "管理后台 - 权限")
@RestController
@RequestMapping("/system/permission")
public class PermissionController {

    @Resource
    private PermissionService permissionService;
    @Resource
    private TenantService tenantService;
    @Resource
    private RoleService roleService;
    @Resource
    private StudentsService studentsService;
    @Resource
    private TeachersService teachersService;

    @Operation(summary = "获得角色拥有的菜单编号")
    @Parameter(name = "roleId", description = "角色编号", required = true)
    @GetMapping("/list-role-resources")
    @PreAuthorize("@ss.hasPermission('system:permission:assign-role-menu')")
    public CommonResult<Set<Long>> listRoleMenus(Long roleId) {
        return success(permissionService.getRoleMenuIds(roleId));
    }

    @PostMapping("/assign-role-menu")
    @Operation(summary = "赋予角色菜单")
    @PreAuthorize("@ss.hasPermission('system:permission:assign-role-menu')")
    public CommonResult<Boolean> assignRoleMenu(@Validated @RequestBody PermissionAssignRoleMenuReqVO reqVO) {
        // 开启多租户的情况下，需要过滤掉未开通的菜单
        tenantService.handleTenantMenu(menuIds -> reqVO.getMenuIds().removeIf(menuId -> !CollUtil.contains(menuIds, menuId)));

        // 执行菜单的分配
        permissionService.assignRoleMenu(reqVO.getRoleId(), reqVO.getMenuIds());
        return success(true);
    }

    @PostMapping("/assign-role-data-scope")
    @Operation(summary = "赋予角色数据权限")
    @PreAuthorize("@ss.hasPermission('system:permission:assign-role-data-scope')")
    public CommonResult<Boolean> assignRoleDataScope(@Valid @RequestBody PermissionAssignRoleDataScopeReqVO reqVO) {
        permissionService.assignRoleDataScope(reqVO.getRoleId(), reqVO.getDataScope(), reqVO.getDataScopeDeptIds());
        return success(true);
    }

    @Operation(summary = "获得管理员拥有的角色编号列表")
    @Parameter(name = "userId", description = "用户编号", required = true)
    @GetMapping("/list-user-roles")
    @PreAuthorize("@ss.hasPermission('system:permission:assign-user-role')")
    public CommonResult<Set<Long>> listAdminRoles(@RequestParam("userId") Long userId) {
        return success(permissionService.getUserRoleIdListByUserId(userId));
    }

    @Operation(summary = "赋予用户角色")
    @PostMapping("/assign-user-role")
    @PreAuthorize("@ss.hasPermission('system:permission:assign-user-role')")
    public CommonResult<Boolean> assignUserRole(@Validated @RequestBody PermissionAssignUserRoleReqVO reqVO) {
        permissionService.assignUserRole(reqVO.getUserId(), reqVO.getRoleIds());
        return success(true);
    }

    @Operation(summary = "赋予用户学生角色")
    @PostMapping("/assign-user-student")
    @PreAuthorize("@ss.hasPermission('system:permission:assign-user-role')")
    public CommonResult<Boolean> assignUserStudentRole(@Validated @RequestBody StudentCreateVO studentReqVO) {
       // 获得学生的roleId
        Set<Long> roleIds = new HashSet<>();
        roleIds.add(roleService.getStudentRoleId());
       //分配学生角色
        permissionService.assignUserRole(studentReqVO.getUserId(),roleIds);
        //创建学生信息
        studentsService.createStudent(studentReqVO);
        return success(true);
    }
    @Operation(summary = "赋予用户导师角色")
    @PostMapping("/assign-user-teacher")
    @PreAuthorize("@ss.hasPermission('system:permission:assign-user-role')")
    public CommonResult<Boolean> assignUserTeacherRole(@Validated @RequestBody TeacherCreateVO teacherReqVO) {
        // 获得导师的roleId
        Set<Long> roleIds = new HashSet<>();
        roleIds.add(roleService.getTeacherRoleId());
        //分配导师角色
        permissionService.assignUserRole(teacherReqVO.getUserId(), roleIds);
        //创建导师信息
        teachersService.createTeacher(teacherReqVO);
        return success(true);
    }
}
