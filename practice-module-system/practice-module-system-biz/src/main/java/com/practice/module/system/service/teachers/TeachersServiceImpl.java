package com.practice.module.system.service.teachers;

import com.practice.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.practice.module.system.controller.admin.students.vo.StudentCreateVO;
import com.practice.module.system.convert.students.StudentsConvert;
import com.practice.module.system.dal.dataobject.students.StudentsDO;
import com.practice.module.system.dal.dataobject.user.AdminUserDO;
import com.practice.module.system.dal.mysql.user.AdminUserMapper;
import com.practice.module.system.service.user.AdminUserService;
import com.practice.module.system.util.valid.IdCardVerification;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import java.util.*;
import com.practice.module.system.controller.admin.teachers.vo.*;
import com.practice.module.system.dal.dataobject.teachers.TeachersDO;
import com.practice.framework.common.pojo.PageResult;

import com.practice.module.system.convert.teachers.TeachersConvert;
import com.practice.module.system.dal.mysql.teachers.TeachersMapper;

import static com.practice.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.practice.module.system.enums.ErrorCodeConstants.*;

/**
 * 导师信息 Service 实现类
 *
 * @author n3
 */
@Service
@Validated
public class TeachersServiceImpl implements TeachersService {

    @Resource
    private TeachersMapper teachersMapper;
    @Resource
    private AdminUserMapper adminUserMapper;
    @Resource
    private AdminUserService adminUserService;
    @Override
    public Long createTeachers(TeachersCreateReqVO createReqVO) {
        // 插入
        TeachersDO teachers = TeachersConvert.INSTANCE.convert(createReqVO);
        teachersMapper.insert(teachers);
        // 返回
        return teachers.getId();
    }

    @Override
    public void updateTeachers(TeachersUpdateReqVO updateReqVO) {
        // 校验存在
        validateTeachersExists(updateReqVO.getId());
        // 更新
        TeachersDO updateObj = TeachersConvert.INSTANCE.convert(updateReqVO);
        teachersMapper.updateById(updateObj);
    }

    @Override
    public void deleteTeachers(Long id) {
        // 校验存在
        validateTeachersExists(id);
        // 删除
        teachersMapper.deleteById(id);
    }

    void validateTeachersExists(Long id) {
        if (teachersMapper.selectById(id) == null) {
            throw exception(TEACHERS_NOT_EXISTS);
        }
    }
    public void validateTeachersExistsByUserId(Long teacherId) {
        if (teachersMapper.selectOne("user_id",teacherId) == null) {
            throw exception(TEACHERS_NOT_EXISTS);
        }
    }
    @Override
    public TeachersDO getTeachers(Long id) {
        return teachersMapper.selectById(id);
    }

    @Override
    public List<TeachersDO> getTeachersList(Collection<Long> ids) {
        return teachersMapper.selectBatchIds(ids);
    }

    @Override
    public PageResult<TeachersDO> getTeachersPage(TeachersPageReqVO pageReqVO) {
        return teachersMapper.selectPage(pageReqVO);
    }

    @Override
    public List<TeachersDO> getTeachersList(TeachersExportReqVO exportReqVO) {
        return teachersMapper.selectList(exportReqVO);
    }


    public Long createTeacher(TeacherCreateVO createVO) {
        //根据userid查找相关数据
        AdminUserDO adminUserDO = adminUserMapper.selectById(createVO.getUserId());
        //验证userid是否存在用户
        adminUserService.validateUserExists(createVO.getUserId());

        createVO.setCardId(createVO.getCardId().toUpperCase());
        //校验身份证号的合理性
        if(!IdCardVerification.IDCardValidate(createVO.getCardId())){
            throw exception(ID_CARD_ERROR);
        }
        TeachersDO teacher = TeachersConvert.INSTANCE.convert(createVO);
        teacher.setName(adminUserDO.getNickname());
        teacher.setMobile(adminUserDO.getMobile());
        teacher.setDeptId(adminUserDO.getDeptId());
        teachersMapper.insert(teacher);
        return teacher.getId();
    }


    public TeachersDO getTeachersWithUserId(Long id) {
        validateTeachersExistsByUserId(id);
        return teachersMapper.selectOne("user_id",id);
    }

}
