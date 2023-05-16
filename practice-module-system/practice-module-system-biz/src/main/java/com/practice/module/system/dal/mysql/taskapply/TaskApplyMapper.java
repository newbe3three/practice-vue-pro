package com.practice.module.system.dal.mysql.taskapply;

import java.util.*;

import com.practice.framework.common.pojo.PageResult;
import com.practice.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.practice.framework.mybatis.core.mapper.BaseMapperX;
import com.practice.module.system.dal.dataobject.taskapply.TaskApplyDO;
import org.apache.ibatis.annotations.Mapper;
import com.practice.module.system.controller.admin.taskapply.vo.*;

/**
 * 任务申请 Mapper
 *
 * @author n3
 */
@Mapper
public interface TaskApplyMapper extends BaseMapperX<TaskApplyDO> {

    default PageResult<TaskApplyDO> selectPage(TaskApplyPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<TaskApplyDO>()
                .likeIfPresent(TaskApplyDO::getName, reqVO.getName())
                .eqIfPresent(TaskApplyDO::getDeptId, reqVO.getDeptId())
                .eqIfPresent(TaskApplyDO::getCreator, reqVO.getCreator())
                .eqIfPresent(TaskApplyDO::getTaskId, reqVO.getTaskId())
                .eqIfPresent(TaskApplyDO::getMobile, reqVO.getMobile())
                .eqIfPresent(TaskApplyDO::getStatus, reqVO.getStatus())
                .betweenIfPresent(TaskApplyDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(TaskApplyDO::getId));
    }

    default List<TaskApplyDO> selectList(TaskApplyExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<TaskApplyDO>()
                .likeIfPresent(TaskApplyDO::getName, reqVO.getName())
                .eqIfPresent(TaskApplyDO::getDeptId, reqVO.getDeptId())
                .eqIfPresent(TaskApplyDO::getCreator, reqVO.getCreator())
                .eqIfPresent(TaskApplyDO::getTaskId, reqVO.getTaskId())
                .eqIfPresent(TaskApplyDO::getMobile, reqVO.getMobile())
                .eqIfPresent(TaskApplyDO::getStatus, reqVO.getStatus())
                .betweenIfPresent(TaskApplyDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(TaskApplyDO::getId));
    }
    default List<TaskApplyDO> selectList(Long id, Byte status) {
        return selectList(new LambdaQueryWrapperX<TaskApplyDO>()
                .eqIfPresent(TaskApplyDO::getTaskId, id)
                .eqIfPresent(TaskApplyDO::getStatus, status));
    }

}
