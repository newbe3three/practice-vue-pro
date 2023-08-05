package com.practice.module.system.service.userwork;

import com.practice.module.system.service.user.AdminUserService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import java.util.*;
import com.practice.module.system.controller.admin.userwork.vo.*;
import com.practice.module.system.dal.dataobject.userwork.UserWorkDO;
import com.practice.framework.common.pojo.PageResult;

import com.practice.module.system.convert.userwork.UserWorkConvert;
import com.practice.module.system.dal.mysql.userwork.UserWorkMapper;

import static com.practice.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.practice.framework.security.core.util.SecurityFrameworkUtils.getLoginUserId;
import static com.practice.module.system.enums.ErrorCodeConstants.*;

/**
 * 工作经历 Service 实现类
 *
 * @author n3
 */
@Service
@Validated
public class UserWorkServiceImpl implements UserWorkService {

    @Resource
    private UserWorkMapper userWorkMapper;
    @Resource
    private AdminUserService adminUserService;



    private void validateUserWorkExists(Long id) {
        if (userWorkMapper.selectById(id) == null) {
            throw exception(USER_WORK_NOT_EXISTS);
        }
    }

    @Override
    public UserWorkDO getUserWork(Long id) {
        return userWorkMapper.selectById(id);
    }

    @Override
    public List<UserWorkDO> getUserWorkList(Collection<Long> ids) {
        return userWorkMapper.selectBatchIds(ids);
    }

    @Override
    public PageResult<UserWorkDO> getUserWorkPage(UserWorkPageReqVO pageReqVO) {
        return userWorkMapper.selectPage(pageReqVO);
    }

    @Override
    public List<UserWorkDO> getUserWorkList(UserWorkExportReqVO exportReqVO) {
        return userWorkMapper.selectList(exportReqVO);
    }

    @Override
    public Long createUserWork(UserWorkCreateReqVO createReqVO) {
        //验证用户存在性
        adminUserService.validateUserExists(createReqVO.getUserId());
        //验证开始时间和结束时间
        int time = createReqVO.getEndTime().compareTo(createReqVO.getStartTime());
        if (time < 0) {
            //结束时间小于开始时间 抛出结束时间小于开始时间异常
            throw exception(TASK_CREATE_TIME_ERROR);
        }
        boolean flag = false;
        List<String> list = Arrays.asList("全职","兼职");
        for(String l:list) {
            if (createReqVO.getType().equals(l)) {
                flag = true;
            }
        }
        //验证工作类型
        if (!flag) {
            throw exception(USER_WORK_TYPE_NOT_EXISTS);
        }

        // 插入
        UserWorkDO userWork = UserWorkConvert.INSTANCE.convert(createReqVO);
        userWorkMapper.insert(userWork);
        // 返回
        return userWork.getId();
    }
    @Override
    public void updateUserWork(UserWorkUpdateReqVO updateReqVO) {
        // 校验存在
        validateUserWorkExists(updateReqVO.getId());
        //验证用户存在性
        //验证是否有权修改
        if(getLoginUserId() != userWorkMapper.selectById(updateReqVO.getId()).getUserId()){
            throw exception(USER_WORK_DEL_ERROR);
        }
        //验证开始时间和结束时间
        int time = updateReqVO.getEndTime().compareTo(updateReqVO.getStartTime());
        if (time < 0) {
            //结束时间小于开始时间 抛出结束时间小于开始时间异常
            throw exception(TASK_CREATE_TIME_ERROR);
        }
        boolean flag = false;
        List<String> list = Arrays.asList("全职","兼职");
        for(String l:list) {
            if (updateReqVO.getType().equals(l)) {
                flag = true;
            }
        }
        //验证工作类型
        if (!flag) {
            throw exception(USER_WORK_TYPE_NOT_EXISTS);
        }
        // 更新
        UserWorkDO updateObj = UserWorkConvert.INSTANCE.convert(updateReqVO);
        userWorkMapper.updateById(updateObj);
    }

    @Override
    public void deleteUserWork(Long id,Long userId) {
        // 校验存在
        validateUserWorkExists(id);
        // 校验是否有权删除
        if (userWorkMapper.selectById(id).getUserId() != userId) {
            throw exception(USER_WORK_DEL_ERROR);
        }

        // 删除
        userWorkMapper.deleteById(id);
    }
    public List<UserWorkDO> getUserWorkWithUserId(Long userId){
        adminUserService.validateUserExists(userId);
        return userWorkMapper.selectList("user_id",userId);
    }

}
