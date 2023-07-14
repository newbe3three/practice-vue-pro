package com.practice.module.system.convert.task;

import java.util.*;

import com.practice.framework.common.pojo.PageResult;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import com.practice.module.system.controller.admin.task.vo.*;
import com.practice.module.system.dal.dataobject.task.TaskDO;

/**
 * 任务 Convert
 *
 * @author n3
 */
@Mapper
public interface TaskConvert {

    TaskConvert INSTANCE = Mappers.getMapper(TaskConvert.class);

    TaskDO convert(TaskCreateReqVO bean);
    TaskDO convert(TaskUpdateReqVO bean);

    TaskDO convert(TaskReviewReqVO bean);

    TaskDO convert(TaskRejectReqVO bean);

    TaskRespVO convert(TaskDO bean);

    List<TaskRespVO> convertList(List<TaskDO> list);

    PageResult<TaskRespVO> convertPage(PageResult<TaskDO> page);

    List<TaskExcelVO> convertList02(List<TaskDO> list);

}
