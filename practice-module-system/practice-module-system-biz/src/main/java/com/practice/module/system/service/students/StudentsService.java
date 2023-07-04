package com.practice.module.system.service.students;

import java.util.*;
import javax.validation.*;
import com.practice.module.system.controller.admin.students.vo.*;
import com.practice.module.system.dal.dataobject.students.StudentsDO;
import com.practice.framework.common.pojo.PageResult;

/**
 * 学生信息 Service 接口
 *
 * @author n3
 */
public interface StudentsService {

    /**
     * 创建学生信息
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createStudents(@Valid StudentsCreateReqVO createReqVO);

    /**
     * 更新学生信息
     *
     * @param updateReqVO 更新信息
     */
    void updateStudents(@Valid StudentsUpdateReqVO updateReqVO);

    /**
     * 删除学生信息
     *
     * @param id 编号
     */
    void deleteStudents(Long id);

    /**
     * 获得学生信息
     *
     * @param id 编号
     * @return 学生信息
     */
    StudentsDO getStudents(Long id);

    /**
     * 获得学生信息列表
     *
     * @param ids 编号
     * @return 学生信息列表
     */
    List<StudentsDO> getStudentsList(Collection<Long> ids);

    /**
     * 获得学生信息分页
     *
     * @param pageReqVO 分页查询
     * @return 学生信息分页
     */
    PageResult<StudentsDO> getStudentsPage(StudentsPageReqVO pageReqVO);

    /**
     * 获得学生信息列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 学生信息列表
     */
    List<StudentsDO> getStudentsList(StudentsExportReqVO exportReqVO);
    Long createStudent(StudentCreateVO createVO);

    StudentsDO getStudentWithUserId(Long userId);
}
