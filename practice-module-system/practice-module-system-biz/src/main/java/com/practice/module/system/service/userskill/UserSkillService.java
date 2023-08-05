package com.practice.module.system.service.userskill;

import java.util.*;
import javax.validation.*;
import com.practice.module.system.controller.admin.userskill.vo.*;
import com.practice.module.system.dal.dataobject.userskill.UserSkillDO;
import com.practice.framework.common.pojo.PageResult;

/**
 * 个人技能 Service 接口
 *
 * @author n3
 */
public interface UserSkillService {

    /**
     * 创建个人技能
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createUserSkill(@Valid UserSkillCreateReqVO createReqVO);

    /**
     * 更新个人技能
     *
     * @param updateReqVO 更新信息
     */
    void updateUserSkill(@Valid UserSkillUpdateReqVO updateReqVO);

    /**
     * 删除个人技能
     *
     * @param id 编号
     */
    void deleteUserSkill(Long id,Long userId);

    /**
     * 获得个人技能
     *
     * @param id 编号
     * @return 个人技能
     */
    UserSkillDO getUserSkill(Long id);

    /**
     * 获得个人技能列表
     *
     * @param ids 编号
     * @return 个人技能列表
     */
    List<UserSkillDO> getUserSkillList(Collection<Long> ids);

    /**
     * 获得个人技能分页
     *
     * @param pageReqVO 分页查询
     * @return 个人技能分页
     */
    PageResult<UserSkillDO> getUserSkillPage(UserSkillPageReqVO pageReqVO);

    /**
     * 获得个人技能列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 个人技能列表
     */
    List<UserSkillDO> getUserSkillList(UserSkillExportReqVO exportReqVO);
    /**
     * 根据userId获得个人技能
     *
     * @param userId 编号
     * @return 个人技能列表
     */
    List<UserSkillDO> getUserSkillWithUserId(Long userId);
}
