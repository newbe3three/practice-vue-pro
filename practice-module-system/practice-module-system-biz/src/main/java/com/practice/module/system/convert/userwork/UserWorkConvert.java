package com.practice.module.system.convert.userwork;

import java.util.*;

import com.practice.framework.common.pojo.PageResult;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import com.practice.module.system.controller.admin.userwork.vo.*;
import com.practice.module.system.dal.dataobject.userwork.UserWorkDO;

/**
 * 工作经历 Convert
 *
 * @author n3
 */
@Mapper
public interface UserWorkConvert {

    UserWorkConvert INSTANCE = Mappers.getMapper(UserWorkConvert.class);

    UserWorkDO convert(UserWorkCreateReqVO bean);

    UserWorkDO convert(UserWorkUpdateReqVO bean);

    UserWorkRespVO convert(UserWorkDO bean);

    List<UserWorkRespVO> convertList(List<UserWorkDO> list);

    PageResult<UserWorkRespVO> convertPage(PageResult<UserWorkDO> page);

    List<UserWorkExcelVO> convertList02(List<UserWorkDO> list);

}
