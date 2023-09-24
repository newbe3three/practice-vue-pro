package com.practice.module.system.convert.worklog;

import java.util.*;

import com.practice.framework.common.pojo.PageResult;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import com.practice.module.system.controller.admin.worklog.vo.*;
import com.practice.module.system.dal.dataobject.worklog.WorkLogDO;

/**
 * 工作日志 Convert
 *
 * @author 觅践
 */
@Mapper
public interface WorkLogConvert {

    WorkLogConvert INSTANCE = Mappers.getMapper(WorkLogConvert.class);

    WorkLogDO convert(WorkLogCreateReqVO bean);

    WorkLogDO convert(WorkLogUpdateReqVO bean);

    WorkLogRespVO convert(WorkLogDO bean);

    List<WorkLogRespVO> convertList(List<WorkLogDO> list);

    PageResult<WorkLogRespVO> convertPage(PageResult<WorkLogDO> page);

    List<WorkLogExcelVO> convertList02(List<WorkLogDO> list);

}
