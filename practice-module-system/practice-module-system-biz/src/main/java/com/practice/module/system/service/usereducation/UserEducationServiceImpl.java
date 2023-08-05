package com.practice.module.system.service.usereducation;

import com.practice.module.system.dal.mysql.user.AdminUserMapper;
import com.practice.module.system.service.user.AdminUserService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import java.util.*;
import com.practice.module.system.controller.admin.usereducation.vo.*;
import com.practice.module.system.dal.dataobject.usereducation.UserEducationDO;
import com.practice.framework.common.pojo.PageResult;

import com.practice.module.system.convert.usereducation.UserEducationConvert;
import com.practice.module.system.dal.mysql.usereducation.UserEducationMapper;

import static com.practice.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.practice.module.system.enums.ErrorCodeConstants.*;
import static com.practice.framework.security.core.util.SecurityFrameworkUtils.getLoginUserId;

/**
 * 教育经历 Service 实现类
 *
 * @author n3
 */
@Service
@Validated
public class UserEducationServiceImpl implements UserEducationService {

    @Resource
    private UserEducationMapper userEducationMapper;
    @Resource
    private AdminUserService adminUserService;



    private void validateUserEducationExists(Long id) {
        if (userEducationMapper.selectById(id) == null) {
            throw exception(USER_EDUCATION_NOT_EXISTS);
        }
    }

    @Override
    public UserEducationDO getUserEducation(Long id) {
        return userEducationMapper.selectById(id);
    }

    @Override
    public List<UserEducationDO> getUserEducationList(Collection<Long> ids) {
        return userEducationMapper.selectBatchIds(ids);
    }

    @Override
    public PageResult<UserEducationDO> getUserEducationPage(UserEducationPageReqVO pageReqVO) {
        return userEducationMapper.selectPage(pageReqVO);
    }

    @Override
    public List<UserEducationDO> getUserEducationList(UserEducationExportReqVO exportReqVO) {
        return userEducationMapper.selectList(exportReqVO);
    }

    @Override
    public List<UserEducationDO> getUserEducationWithUserId(Long userId) {
        adminUserService.validateUserExists(userId);
        return userEducationMapper.selectList("user_id",userId);
    }

    @Override
    public Long createUserEducation(UserEducationCreateReqVO createReqVO) {
        //验证userid
        adminUserService.validateUserExists(createReqVO.getUserId());
        //验证开始时间和结束时间
        int time = createReqVO.getEndTime().compareTo(createReqVO.getStartTime());
        if (time < 0) {
            //结束时间小于开始时间 抛出结束时间小于开始时间异常
            throw exception(TASK_CREATE_TIME_ERROR);
        }
        // 插入
        UserEducationDO userEducation = UserEducationConvert.INSTANCE.convert(createReqVO);
        userEducationMapper.insert(userEducation);
        // 返回
        return userEducation.getId();
    }
    @Override
    public void updateUserEducation(UserEducationUpdateReqVO updateReqVO) {
        // 校验存在
        validateUserEducationExists(updateReqVO.getId());
        //验证用户存在性
        //验证是否有权删除
        if(getLoginUserId() != userEducationMapper.selectById(updateReqVO.getId()).getUserId()){
            throw exception(USER_EDUCATION_DEL_ERROR);
        }
        //验证开始时间和结束时间
        int time = updateReqVO.getEndTime().compareTo(updateReqVO.getStartTime());
        if (time < 0) {
            //结束时间小于开始时间 抛出结束时间小于开始时间异常
            throw exception(TASK_CREATE_TIME_ERROR);
        }
        // 更新
        UserEducationDO updateObj = UserEducationConvert.INSTANCE.convert(updateReqVO);
        userEducationMapper.updateById(updateObj);
    }

    @Override
    public void deleteUserEducation(Long id,Long userId) {
        // 校验存在
        validateUserEducationExists(id);
        //验证是否有权删除
        if(userId != userEducationMapper.selectById(id).getUserId()){
            throw exception(USER_EDUCATION_DEL_ERROR);
        }
        // 删除
        userEducationMapper.deleteById(id);
    }


}
