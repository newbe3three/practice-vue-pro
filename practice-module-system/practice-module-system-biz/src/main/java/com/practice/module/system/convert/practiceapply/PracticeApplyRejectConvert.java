package com.practice.module.system.convert.practiceapply;

import com.practice.module.system.controller.admin.practiceapply.vo.reject.PracticeApplyRejectCreateReqVO;
import com.practice.module.system.controller.admin.practiceapply.vo.reject.PracticeApplyRejectRespVO;
import com.practice.module.system.dal.dataobject.practiceapply.PracticeApplyRejectDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface PracticeApplyRejectConvert {
    PracticeApplyRejectConvert INSTANCE = Mappers.getMapper(PracticeApplyRejectConvert.class);
    List<PracticeApplyRejectRespVO> convertList(List<PracticeApplyRejectDO> list);
    PracticeApplyRejectDO convert(PracticeApplyRejectCreateReqVO bean);


}
