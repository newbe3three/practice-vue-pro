package com.practice.module.system.dal.mysql.task;

import java.util.*;

import com.practice.framework.common.pojo.PageResult;
import com.practice.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.practice.framework.mybatis.core.mapper.BaseMapperX;
import com.practice.module.system.dal.dataobject.task.TaskDO;
import org.apache.ibatis.annotations.Mapper;
import com.practice.module.system.controller.admin.task.vo.*;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * 任务 Mapper
 *
 * @author n3
 */
@Mapper
public interface TaskMapper extends BaseMapperX<TaskDO> {

    default PageResult<TaskDO> selectPage(TaskPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<TaskDO>()
                .likeIfPresent(TaskDO::getName, reqVO.getName())
                .eqIfPresent(TaskDO::getCompanyId, reqVO.getCompanyId())
                .eqIfPresent(TaskDO::getAmount, reqVO.getAmount())
                .betweenIfPresent(TaskDO::getStartTime, reqVO.getStartTime())
                .betweenIfPresent(TaskDO::getEndTime, reqVO.getEndTime())
                .eqIfPresent(TaskDO::getNumberPeople, reqVO.getNumberPeople())
                .eqIfPresent(TaskDO::getStatus, reqVO.getStatus())
                .eqIfPresent(TaskDO::getSuggestion, reqVO.getSuggestion())
                .betweenIfPresent(TaskDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(TaskDO::getId));
    }

    default List<TaskDO> selectList(TaskExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<TaskDO>()
                .likeIfPresent(TaskDO::getName, reqVO.getName())
                .eqIfPresent(TaskDO::getCompanyId, reqVO.getCompanyId())
                .eqIfPresent(TaskDO::getAmount, reqVO.getAmount())
                .betweenIfPresent(TaskDO::getStartTime, reqVO.getStartTime())
                .betweenIfPresent(TaskDO::getEndTime, reqVO.getEndTime())
                .eqIfPresent(TaskDO::getNumberPeople, reqVO.getNumberPeople())
                .eqIfPresent(TaskDO::getStatus, reqVO.getStatus())
                .eqIfPresent(TaskDO::getSuggestion, reqVO.getSuggestion())
                .betweenIfPresent(TaskDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(TaskDO::getId));
    }

}
