package com.practice.module.system.convert.practiceschool;

import java.util.*;

import com.practice.framework.common.pojo.PageResult;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import com.practice.module.system.controller.admin.practiceschool.vo.*;
import com.practice.module.system.dal.dataobject.practiceschool.PracticeSchoolDO;

/**
 * 学校申请实践 Convert
 *
 * @author n3
 */
@Mapper
public interface PracticeSchoolConvert {

    PracticeSchoolConvert INSTANCE = Mappers.getMapper(PracticeSchoolConvert.class);

    PracticeSchoolDO convert(PracticeSchoolCreateReqVO bean);

    PracticeSchoolDO convert(PracticeSchoolUpdateReqVO bean);

    PracticeSchoolRespVO convert(PracticeSchoolDO bean);

    List<PracticeSchoolRespVO> convertList(List<PracticeSchoolDO> list);

    PageResult<PracticeSchoolRespVO> convertPage(PageResult<PracticeSchoolDO> page);

    List<PracticeSchoolExcelVO> convertList02(List<PracticeSchoolDO> list);

}
