package com.practice.module.system.convert.signin;

import java.util.*;

import com.practice.framework.common.pojo.PageResult;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import com.practice.module.system.controller.admin.signin.vo.*;
import com.practice.module.system.dal.dataobject.signin.SignInDO;

/**
 * 签到 Convert
 *
 * @author 觅践
 */
@Mapper
public interface SignInConvert {

    SignInConvert INSTANCE = Mappers.getMapper(SignInConvert.class);

    SignInDO convert(SignInCreateReqVO bean);

    SignInDO convert(SignInUpdateReqVO bean);

    SignInRespVO convert(SignInDO bean);

    List<SignInRespVO> convertList(List<SignInDO> list);

    PageResult<SignInRespVO> convertPage(PageResult<SignInDO> page);

    List<SignInExcelVO> convertList02(List<SignInDO> list);

}
