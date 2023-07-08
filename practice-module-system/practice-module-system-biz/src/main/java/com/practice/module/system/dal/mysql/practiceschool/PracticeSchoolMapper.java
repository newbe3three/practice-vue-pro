package com.practice.module.system.dal.mysql.practiceschool;

import java.util.*;

import com.practice.framework.common.pojo.PageResult;
import com.practice.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.practice.framework.mybatis.core.mapper.BaseMapperX;
import com.practice.module.system.controller.admin.practice.vo.practice.PracticeIdPageReqVO;
import com.practice.module.system.dal.dataobject.practiceschool.PracticeSchoolDO;
import org.apache.ibatis.annotations.Mapper;
import com.practice.module.system.controller.admin.practiceschool.vo.*;

/**
 * 学校申请实践 Mapper
 *
 * @author n3
 */
@Mapper
public interface PracticeSchoolMapper extends BaseMapperX<PracticeSchoolDO> {

    default PageResult<PracticeSchoolDO> selectPage(PracticeSchoolPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<PracticeSchoolDO>()
                .eqIfPresent(PracticeSchoolDO::getSchoolId, reqVO.getSchoolId())
                .eqIfPresent(PracticeSchoolDO::getPracticeId, reqVO.getPracticeId())
                .eqIfPresent(PracticeSchoolDO::getStatus, reqVO.getStatus())
                .betweenIfPresent(PracticeSchoolDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(PracticeSchoolDO::getId));
    }

    default List<PracticeSchoolDO> selectList(PracticeSchoolExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<PracticeSchoolDO>()
                .eqIfPresent(PracticeSchoolDO::getSchoolId, reqVO.getSchoolId())
                .eqIfPresent(PracticeSchoolDO::getPracticeId, reqVO.getPracticeId())
                .eqIfPresent(PracticeSchoolDO::getStatus, reqVO.getStatus())
                .betweenIfPresent(PracticeSchoolDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(PracticeSchoolDO::getId));
    }

    default List<PracticeSchoolDO> selectList(PracticeSchoolDO practiceSchoolDO) {
        return selectList(new LambdaQueryWrapperX<PracticeSchoolDO>()
                .eqIfPresent(PracticeSchoolDO::getSchoolId, practiceSchoolDO.getSchoolId())
                .eqIfPresent(PracticeSchoolDO::getStatus, practiceSchoolDO.getStatus())
                .orderByDesc(PracticeSchoolDO::getId));
    }
    //根据本企业的实践id列表查询 相关的学校申请
    // select * from school where practiceId in (list)
    default PageResult<PracticeSchoolDO> selectPage(PracticeIdPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<PracticeSchoolDO>()
                .inIfPresent(PracticeSchoolDO::getPracticeId,reqVO.getPracticeIdList())
                .orderByDesc(PracticeSchoolDO::getId));
    }


}
