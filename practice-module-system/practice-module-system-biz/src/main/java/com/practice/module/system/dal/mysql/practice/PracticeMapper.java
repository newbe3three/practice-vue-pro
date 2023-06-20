package com.practice.module.system.dal.mysql.practice;

import java.util.*;

import com.practice.framework.common.pojo.PageResult;
import com.practice.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.practice.framework.mybatis.core.mapper.BaseMapperX;
import com.practice.module.system.controller.admin.practice.vo.practice.PracticeExportReqVO;
import com.practice.module.system.controller.admin.practice.vo.practice.PracticePageReqVO;
import com.practice.module.system.dal.dataobject.practice.PracticeDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 实践 Mapper
 *
 * @author n3
 */
@Mapper
public interface PracticeMapper extends BaseMapperX<PracticeDO> {

    default PageResult<PracticeDO> selectPage(PracticePageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<PracticeDO>()
                .likeIfPresent(PracticeDO::getName, reqVO.getName())
                .eqIfPresent(PracticeDO::getContent, reqVO.getContent())
                .betweenIfPresent(PracticeDO::getStartTime, reqVO.getStartTime())
                .betweenIfPresent(PracticeDO::getEndTime, reqVO.getEndTime())
                .eqIfPresent(PracticeDO::getNumberPeople, reqVO.getNumberPeople())
                .eqIfPresent(PracticeDO::getStatus, reqVO.getStatus())
                .betweenIfPresent(PracticeDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(PracticeDO::getId));
    }

    default List<PracticeDO> selectList(PracticeExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<PracticeDO>()
                .likeIfPresent(PracticeDO::getName, reqVO.getName())
                .eqIfPresent(PracticeDO::getContent, reqVO.getContent())
                .betweenIfPresent(PracticeDO::getStartTime, reqVO.getStartTime())
                .betweenIfPresent(PracticeDO::getEndTime, reqVO.getEndTime())
                .eqIfPresent(PracticeDO::getNumberPeople, reqVO.getNumberPeople())
                .eqIfPresent(PracticeDO::getStatus, reqVO.getStatus())
                .betweenIfPresent(PracticeDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(PracticeDO::getId));
    }

}
