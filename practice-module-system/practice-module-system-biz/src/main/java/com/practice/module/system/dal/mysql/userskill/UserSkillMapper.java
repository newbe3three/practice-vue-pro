package com.practice.module.system.dal.mysql.userskill;

import java.util.*;

import com.practice.framework.common.pojo.PageResult;
import com.practice.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.practice.framework.mybatis.core.mapper.BaseMapperX;
import com.practice.module.system.dal.dataobject.userskill.UserSkillDO;
import org.apache.ibatis.annotations.Mapper;
import com.practice.module.system.controller.admin.userskill.vo.*;

/**
 * 个人技能 Mapper
 *
 * @author n3
 */
@Mapper
public interface UserSkillMapper extends BaseMapperX<UserSkillDO> {

    default PageResult<UserSkillDO> selectPage(UserSkillPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<UserSkillDO>()
                .eqIfPresent(UserSkillDO::getUserId, reqVO.getUserId())
                .eqIfPresent(UserSkillDO::getSkill, reqVO.getSkill())
                .eqIfPresent(UserSkillDO::getLevel, reqVO.getLevel())
                .betweenIfPresent(UserSkillDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(UserSkillDO::getId));
    }

    default List<UserSkillDO> selectList(UserSkillExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<UserSkillDO>()
                .eqIfPresent(UserSkillDO::getUserId, reqVO.getUserId())
                .eqIfPresent(UserSkillDO::getSkill, reqVO.getSkill())
                .eqIfPresent(UserSkillDO::getLevel, reqVO.getLevel())
                .betweenIfPresent(UserSkillDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(UserSkillDO::getId));
    }

}
