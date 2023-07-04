package com.practice.module.system.dal.mysql.practiceapply;

import java.util.*;

import com.practice.framework.common.pojo.PageResult;
import com.practice.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.practice.framework.mybatis.core.mapper.BaseMapperX;
import com.practice.module.system.controller.admin.practiceapply.vo.apply.PracticeApplyExportReqVO;
import com.practice.module.system.controller.admin.practiceapply.vo.apply.PracticeApplyPageReqVO;
import com.practice.module.system.dal.dataobject.practiceapply.PracticeApplyDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 实践申请 Mapper
 *
 * @author n3
 */
@Mapper
public interface PracticeApplyMapper extends BaseMapperX<PracticeApplyDO> {

    default PageResult<PracticeApplyDO> selectPage(PracticeApplyPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<PracticeApplyDO>()
                .eqIfPresent(PracticeApplyDO::getUserId, reqVO.getUserId())
                .eqIfPresent(PracticeApplyDO::getResume, reqVO.getResume())
                .eqIfPresent(PracticeApplyDO::getMessage, reqVO.getMessage())
                .eqIfPresent(PracticeApplyDO::getStatus, reqVO.getStatus())
                .betweenIfPresent(PracticeApplyDO::getCreateTime, reqVO.getCreateTime())
                .eqIfPresent(PracticeApplyDO::getPracticeId, reqVO.getPracticeId())
                .orderByDesc(PracticeApplyDO::getId));
    }

    default List<PracticeApplyDO> selectList(PracticeApplyExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<PracticeApplyDO>()
                .eqIfPresent(PracticeApplyDO::getUserId, reqVO.getUserId())
                .eqIfPresent(PracticeApplyDO::getResume, reqVO.getResume())
                .eqIfPresent(PracticeApplyDO::getMessage, reqVO.getMessage())
                .eqIfPresent(PracticeApplyDO::getStatus, reqVO.getStatus())
                .betweenIfPresent(PracticeApplyDO::getCreateTime, reqVO.getCreateTime())
                .eqIfPresent(PracticeApplyDO::getPracticeId, reqVO.getPracticeId())
                .orderByDesc(PracticeApplyDO::getId));
    }

    default List<PracticeApplyDO> selectWithUserIdAndPracticeId(Long userId,Long practiceId) {
        return selectList(new LambdaQueryWrapperX<PracticeApplyDO>()
                .eqIfPresent(PracticeApplyDO::getUserId, userId)
                .eqIfPresent(PracticeApplyDO::getPracticeId, practiceId)
               );
    }

}
