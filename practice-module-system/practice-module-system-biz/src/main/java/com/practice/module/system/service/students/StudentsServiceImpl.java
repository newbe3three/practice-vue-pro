package com.practice.module.system.service.students;

import com.practice.module.system.dal.dataobject.user.AdminUserDO;
import com.practice.module.system.dal.mysql.user.AdminUserMapper;
import com.practice.module.system.service.teachers.TeachersService;
import com.practice.module.system.service.user.AdminUserService;
import com.practice.module.system.util.valid.IdCardVerification;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import java.text.ParseException;
import java.util.*;
import com.practice.module.system.controller.admin.students.vo.*;
import com.practice.module.system.dal.dataobject.students.StudentsDO;
import com.practice.framework.common.pojo.PageResult;

import com.practice.module.system.convert.students.StudentsConvert;
import com.practice.module.system.dal.mysql.students.StudentsMapper;

import static com.practice.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.practice.module.system.enums.ErrorCodeConstants.*;

/**
 * 学生信息 Service 实现类
 *
 * @author n3
 */
@Service
@Validated
public class StudentsServiceImpl implements StudentsService {

    @Resource
    private StudentsMapper studentsMapper;
    @Resource
    private AdminUserMapper adminUserMapper;
    @Resource
    private AdminUserService adminUserService;
    @Resource
    private TeachersService teachersService;
    @Override
    public Long createStudents(StudentsCreateReqVO createReqVO) {
        //验证开始时间和结束时间
        int time = createReqVO.getEndTime().compareTo(createReqVO.getStartTime());
        if (time < 0) {
            //结束时间小于开始时间 抛出结束时间小于开始时间异常
            throw exception(TASK_CREATE_TIME_ERROR);
        }
        // 插入
        StudentsDO students = StudentsConvert.INSTANCE.convert(createReqVO);
        studentsMapper.insert(students);
        // 返回
        return students.getId();
    }

    @Override
    public void updateStudents(StudentsUpdateReqVO updateReqVO) {
        // 校验存在
        validateStudentsExists(updateReqVO.getId());
        // 更新
        StudentsDO updateObj = StudentsConvert.INSTANCE.convert(updateReqVO);
        studentsMapper.updateById(updateObj);
    }

    @Override
    public void deleteStudents(Long id) {
        // 校验存在
        validateStudentsExists(id);
        // 删除
        studentsMapper.deleteById(id);
    }

    private void validateStudentsExists(Long id) {
        if (studentsMapper.selectById(id) == null) {
            throw exception(STUDENTS_NOT_EXISTS);
        }
    }
    private void validateStudentsExistsByUserId(Long id) {
        if (studentsMapper.selectOne("user_id",id) == null) {
            throw exception(STUDENTS_NOT_EXISTS);
        }
    }
    @Override
    public StudentsDO getStudents(Long id) {
        return studentsMapper.selectById(id);
    }

    @Override
    public List<StudentsDO> getStudentsList(Collection<Long> ids) {
        return studentsMapper.selectBatchIds(ids);
    }

    @Override
    public PageResult<StudentsDO> getStudentsPage(StudentsPageReqVO pageReqVO) {
        return studentsMapper.selectPage(pageReqVO);
    }

    @Override
    public List<StudentsDO> getStudentsList(StudentsExportReqVO exportReqVO) {
        return studentsMapper.selectList(exportReqVO);
    }

    public Long createStudent(StudentCreateVO createVO) {
        //根据userid查找相关数据
        AdminUserDO adminUserDO = adminUserMapper.selectById(createVO.getUserId());
        //验证userid是否存在用户
        adminUserService.validateUserExists(createVO.getUserId());
        //验证teacherId是否存在 teacher 是否存在
        teachersService.validateTeachersExistsByUserId(createVO.getTeacherId());
       createVO.setCardId(createVO.getCardId().toUpperCase());
        //校验身份证号的合理性
        if(!IdCardVerification.IDCardValidate(createVO.getCardId())){
            throw exception(ID_CARD_ERROR);
        }
        StudentsDO student = StudentsConvert.INSTANCE.convert(createVO);
        student.setName(adminUserDO.getNickname());
        student.setMobile(adminUserDO.getMobile());
        student.setDeptId(adminUserDO.getDeptId());
        studentsMapper.insert(student);
        return student.getId();
    }
    public StudentsDO getStudentWithUserId(Long userId) {
        validateStudentsExistsByUserId(userId);
        StudentsDO student = studentsMapper.selectOne("user_id", userId);
        return student;
    }

}
