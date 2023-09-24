package com.practice.module.system.dal.mysql.worklog;

import java.util.*;

import com.practice.framework.common.pojo.PageResult;
import com.practice.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.practice.framework.mybatis.core.mapper.BaseMapperX;
import com.practice.module.system.dal.dataobject.worklog.WorkLogDO;
import org.apache.ibatis.annotations.Mapper;
import com.practice.module.system.controller.admin.worklog.vo.*;

/**
 * 工作日志 Mapper
 *
 * @author 觅践
 */
@Mapper
public interface WorkLogMapper extends BaseMapperX<WorkLogDO> {

    default PageResult<WorkLogDO> selectPage(WorkLogPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<WorkLogDO>()
                .eqIfPresent(WorkLogDO::getUserId, reqVO.getUserId())
                .eqIfPresent(WorkLogDO::getPracticeId, reqVO.getPracticeId())
                .betweenIfPresent(WorkLogDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(WorkLogDO::getId));
    }

    default List<WorkLogDO> selectList(WorkLogExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<WorkLogDO>()
                .eqIfPresent(WorkLogDO::getUserId, reqVO.getUserId())
                .eqIfPresent(WorkLogDO::getPracticeId, reqVO.getPracticeId())
                .betweenIfPresent(WorkLogDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(WorkLogDO::getId));
    }

}
