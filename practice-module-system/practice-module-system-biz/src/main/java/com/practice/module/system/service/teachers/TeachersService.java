package com.practice.module.system.service.teachers;

import java.util.*;
import javax.validation.*;
import com.practice.module.system.controller.admin.teachers.vo.*;
import com.practice.module.system.dal.dataobject.teachers.TeachersDO;
import com.practice.framework.common.pojo.PageResult;

/**
 * 导师信息 Service 接口
 *
 * @author n3
 */
public interface TeachersService {

    /**
     * 创建导师信息
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createTeachers(@Valid TeachersCreateReqVO createReqVO);

    /**
     * 更新导师信息
     *
     * @param updateReqVO 更新信息
     */
    void updateTeachers(@Valid TeachersUpdateReqVO updateReqVO);

    /**
     * 删除导师信息
     *
     * @param id 编号
     */
    void deleteTeachers(Long id);

    /**
     * 获得导师信息
     *
     * @param id 编号
     * @return 导师信息
     */
    TeachersDO getTeachers(Long id);

    /**
     * 获得导师信息列表
     *
     * @param ids 编号
     * @return 导师信息列表
     */
    List<TeachersDO> getTeachersList(Collection<Long> ids);

    /**
     * 获得导师信息分页
     *
     * @param pageReqVO 分页查询
     * @return 导师信息分页
     */
    PageResult<TeachersDO> getTeachersPage(TeachersPageReqVO pageReqVO);

    /**
     * 获得导师信息列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 导师信息列表
     */
    List<TeachersDO> getTeachersList(TeachersExportReqVO exportReqVO);
    void validateTeachersExistsByUserId(Long id);
    Long createTeacher(TeacherCreateVO teacherCreateVO);
    TeachersDO getTeachersWithUserId(Long id);
}
