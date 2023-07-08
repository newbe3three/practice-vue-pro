package com.practice.module.system.convert.practice;

import java.util.*;

import com.practice.framework.common.pojo.PageResult;

import com.practice.module.system.controller.admin.practice.vo.practice.PracticeCreateReqVO;
import com.practice.module.system.controller.admin.practice.vo.practice.PracticeExcelVO;
import com.practice.module.system.controller.admin.practice.vo.practice.PracticeRespVO;
import com.practice.module.system.controller.admin.practice.vo.practice.PracticeUpdateReqVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import com.practice.module.system.dal.dataobject.practice.PracticeDO;

/**
 * 实践 Convert
 *
 * @author n3
 */
@Mapper
public interface PracticeConvert {

    PracticeConvert INSTANCE = Mappers.getMapper(PracticeConvert.class);

    PracticeDO convert(PracticeCreateReqVO bean);
    PracticeDO convert2(PracticeCreateReqVO bean);
    PracticeDO convert(PracticeUpdateReqVO bean);

    PracticeRespVO convert(PracticeDO bean);

    List<PracticeRespVO> convertList(List<PracticeDO> list);

    PageResult<PracticeRespVO> convertPage(PageResult<PracticeDO> page);
    PageResult<PracticeRespVO> convertPage2(PageResult<PracticeDO> page);
    List<PracticeExcelVO> convertList02(List<PracticeDO> list);

}
