package com.practice.module.system.dal.mysql.userwork;

import java.util.*;

import com.practice.framework.common.pojo.PageResult;
import com.practice.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.practice.framework.mybatis.core.mapper.BaseMapperX;
import com.practice.module.system.dal.dataobject.userwork.UserWorkDO;
import org.apache.ibatis.annotations.Mapper;
import com.practice.module.system.controller.admin.userwork.vo.*;

/**
 * 工作经历 Mapper
 *
 * @author n3
 */
@Mapper
public interface UserWorkMapper extends BaseMapperX<UserWorkDO> {

    default PageResult<UserWorkDO> selectPage(UserWorkPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<UserWorkDO>()
                .eqIfPresent(UserWorkDO::getUserId, reqVO.getUserId())
                .likeIfPresent(UserWorkDO::getName, reqVO.getName())
                .eqIfPresent(UserWorkDO::getType, reqVO.getType())
                .likeIfPresent(UserWorkDO::getCompanyName, reqVO.getCompanyName())
                .eqIfPresent(UserWorkDO::getDescription, reqVO.getDescription())
                .betweenIfPresent(UserWorkDO::getCreateTime, reqVO.getCreateTime())
                .betweenIfPresent(UserWorkDO::getStartTime, reqVO.getStartTime())
                .betweenIfPresent(UserWorkDO::getEndTime, reqVO.getEndTime())
                .orderByDesc(UserWorkDO::getId));
    }

    default List<UserWorkDO> selectList(UserWorkExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<UserWorkDO>()
                .eqIfPresent(UserWorkDO::getUserId, reqVO.getUserId())
                .likeIfPresent(UserWorkDO::getName, reqVO.getName())
                .eqIfPresent(UserWorkDO::getType, reqVO.getType())
                .likeIfPresent(UserWorkDO::getCompanyName, reqVO.getCompanyName())
                .eqIfPresent(UserWorkDO::getDescription, reqVO.getDescription())
                .betweenIfPresent(UserWorkDO::getCreateTime, reqVO.getCreateTime())
                .betweenIfPresent(UserWorkDO::getStartTime, reqVO.getStartTime())
                .betweenIfPresent(UserWorkDO::getEndTime, reqVO.getEndTime())
                .orderByDesc(UserWorkDO::getId));
    }

}
