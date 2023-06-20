package com.practice.module.system.convert.practice;

import com.practice.module.system.controller.admin.practice.vo.reject.PracticeRejectCreateReqVO;
import com.practice.module.system.controller.admin.practice.vo.reject.PracticeRejectRespVO;
import com.practice.module.system.dal.dataobject.practice.PracticeRejectDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 实践驳回记录 Convert
 *
 * @author n3
 */
@Mapper
public interface PracticeRejectConvert {

    PracticeRejectConvert INSTANCE = Mappers.getMapper(PracticeRejectConvert.class);

    PracticeRejectDO convert(PracticeRejectCreateReqVO bean);

    PracticeRejectRespVO convert(PracticeRejectDO bean);

    List<PracticeRejectRespVO> convertList(List<PracticeRejectDO> list);
}
