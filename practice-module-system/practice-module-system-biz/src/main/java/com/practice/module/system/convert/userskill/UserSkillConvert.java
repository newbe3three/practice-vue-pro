package com.practice.module.system.convert.userskill;

import java.util.*;

import com.practice.framework.common.pojo.PageResult;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import com.practice.module.system.controller.admin.userskill.vo.*;
import com.practice.module.system.dal.dataobject.userskill.UserSkillDO;

/**
 * 个人技能 Convert
 *
 * @author n3
 */
@Mapper
public interface UserSkillConvert {

    UserSkillConvert INSTANCE = Mappers.getMapper(UserSkillConvert.class);

    UserSkillDO convert(UserSkillCreateReqVO bean);

    UserSkillDO convert(UserSkillUpdateReqVO bean);

    UserSkillRespVO convert(UserSkillDO bean);

    List<UserSkillRespVO> convertList(List<UserSkillDO> list);

    PageResult<UserSkillRespVO> convertPage(PageResult<UserSkillDO> page);

    List<UserSkillExcelVO> convertList02(List<UserSkillDO> list);

}
