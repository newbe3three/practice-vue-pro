package com.practice.module.system.convert.usereducation;

import java.util.*;

import com.practice.framework.common.pojo.PageResult;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import com.practice.module.system.controller.admin.usereducation.vo.*;
import com.practice.module.system.dal.dataobject.usereducation.UserEducationDO;

/**
 * 教育经历 Convert
 *
 * @author n3
 */
@Mapper
public interface UserEducationConvert {

    UserEducationConvert INSTANCE = Mappers.getMapper(UserEducationConvert.class);

    UserEducationDO convert(UserEducationCreateReqVO bean);

    UserEducationDO convert(UserEducationUpdateReqVO bean);

    UserEducationRespVO convert(UserEducationDO bean);

    List<UserEducationRespVO> convertList(List<UserEducationDO> list);

    PageResult<UserEducationRespVO> convertPage(PageResult<UserEducationDO> page);

    List<UserEducationExcelVO> convertList02(List<UserEducationDO> list);

}
