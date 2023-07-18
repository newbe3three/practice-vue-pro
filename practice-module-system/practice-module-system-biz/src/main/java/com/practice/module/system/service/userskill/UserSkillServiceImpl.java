package com.practice.module.system.service.userskill;

import com.practice.module.system.dal.mysql.user.AdminUserMapper;
import com.practice.module.system.service.user.AdminUserService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import java.util.*;
import com.practice.module.system.controller.admin.userskill.vo.*;
import com.practice.module.system.dal.dataobject.userskill.UserSkillDO;
import com.practice.framework.common.pojo.PageResult;

import com.practice.module.system.convert.userskill.UserSkillConvert;
import com.practice.module.system.dal.mysql.userskill.UserSkillMapper;

import static com.practice.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.practice.module.system.enums.ErrorCodeConstants.*;

/**
 * 个人技能 Service 实现类
 *
 * @author n3
 */
@Service
@Validated
public class UserSkillServiceImpl implements UserSkillService {

    @Resource
    private UserSkillMapper userSkillMapper;
    @Resource
    private AdminUserService adminUserService;







    private void validateUserSkillExists(Long id) {
        if (userSkillMapper.selectById(id) == null) {
            throw exception(USER_SKILL_NOT_EXISTS);
        }
    }

    @Override
    public UserSkillDO getUserSkill(Long id) {
        return userSkillMapper.selectById(id);
    }

    @Override
    public List<UserSkillDO> getUserSkillList(Collection<Long> ids) {
        return userSkillMapper.selectBatchIds(ids);
    }

    @Override
    public PageResult<UserSkillDO> getUserSkillPage(UserSkillPageReqVO pageReqVO) {
        return userSkillMapper.selectPage(pageReqVO);
    }

    @Override
    public List<UserSkillDO> getUserSkillList(UserSkillExportReqVO exportReqVO) {
        return userSkillMapper.selectList(exportReqVO);
    }


    @Override
    public Long createUserSkill(UserSkillCreateReqVO createReqVO) {
        //校验用户存在性
        adminUserService.validateUserExists(createReqVO.getUserId());
        List<String> list = Arrays.asList("初级","中级","高级");
        boolean flag = false;
        for (String l:list) {

            if (createReqVO.getLevel().equals(l)) {
                flag = true;
            }
        }

        //检查level是否符合要求
        if(!flag){
            throw exception(USER_SKILL_LEVEL_NOT_EXISTS);
        }
        // 插入
        UserSkillDO userSkill = UserSkillConvert.INSTANCE.convert(createReqVO);
        userSkillMapper.insert(userSkill);
        // 返回
        return userSkill.getId();

    }

    @Override
    public void updateUserSkill(UserSkillUpdateReqVO updateReqVO) {
        // 校验存在
        validateUserSkillExists(updateReqVO.getId());
        //校验用户存在性
        adminUserService.validateUserExists(updateReqVO.getUserId());
        List<String> list = Arrays.asList("初级","中级","高级");
        boolean flag = false;
        for (String l:list) {
            if (updateReqVO.getLevel().equals(l)) {
                flag = true;
            }
        }


        //检查level是否符合要求
        if(!flag){
            throw exception(USER_SKILL_LEVEL_NOT_EXISTS);

        }
        UserSkillDO updateObj = UserSkillConvert.INSTANCE.convert(updateReqVO);
        // 更新
        userSkillMapper.updateById(updateObj);



    }

    @Override
    public void deleteUserSkill(Long id) {
        // 校验存在
        validateUserSkillExists(id);
        // 删除
        userSkillMapper.deleteById(id);
    }
    public List<UserSkillDO> getUserSkillWithUserId(Long userId){
        adminUserService.validateUserExists(userId);
        //根据userId 查询技能列表
        return userSkillMapper.selectList("user_id",userId);
    }

}
