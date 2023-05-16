package com.practice.module.system.convert.taskapply;

import java.util.*;

import com.practice.framework.common.pojo.PageResult;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import com.practice.module.system.controller.admin.taskapply.vo.*;
import com.practice.module.system.dal.dataobject.taskapply.TaskApplyDO;

/**
 * 任务申请 Convert
 *
 * @author n3
 */
@Mapper
public interface TaskApplyConvert {

    TaskApplyConvert INSTANCE = Mappers.getMapper(TaskApplyConvert.class);

    TaskApplyDO convert(TaskApplyCreateReqVO bean);

    TaskApplyDO convert(TaskApplyUpdateReqVO bean);

    TaskApplyRespVO convert(TaskApplyDO bean);

    List<TaskApplyRespVO> convertList(List<TaskApplyDO> list);

    PageResult<TaskApplyRespVO> convertPage(PageResult<TaskApplyDO> page);

    List<TaskApplyExcelVO> convertList02(List<TaskApplyDO> list);

}
