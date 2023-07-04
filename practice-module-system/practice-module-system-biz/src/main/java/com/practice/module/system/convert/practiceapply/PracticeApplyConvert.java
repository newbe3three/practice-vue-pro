package com.practice.module.system.convert.practiceapply;

import java.util.*;

import com.practice.framework.common.pojo.PageResult;

import com.practice.module.system.controller.admin.practiceapply.vo.apply.PracticeApplyCreateReqVO;
import com.practice.module.system.controller.admin.practiceapply.vo.apply.PracticeApplyExcelVO;
import com.practice.module.system.controller.admin.practiceapply.vo.apply.PracticeApplyRespVO;
import com.practice.module.system.controller.admin.practiceapply.vo.apply.PracticeApplyUpdateReqVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import com.practice.module.system.dal.dataobject.practiceapply.PracticeApplyDO;

/**
 * 实践申请 Convert
 *
 * @author n3
 */
@Mapper
public interface PracticeApplyConvert {

    PracticeApplyConvert INSTANCE = Mappers.getMapper(PracticeApplyConvert.class);

    PracticeApplyDO convert2(PracticeApplyCreateReqVO bean);
    PracticeApplyDO convert2(PracticeApplyUpdateReqVO bean);

    PracticeApplyDO convert(PracticeApplyCreateReqVO bean);

    PracticeApplyDO convert(PracticeApplyUpdateReqVO bean);

    PracticeApplyRespVO convert(PracticeApplyDO bean);

    List<PracticeApplyRespVO> convertList(List<PracticeApplyDO> list);

    PageResult<PracticeApplyRespVO> convertPage(PageResult<PracticeApplyDO> page);

    List<PracticeApplyExcelVO> convertList02(List<PracticeApplyDO> list);

}
