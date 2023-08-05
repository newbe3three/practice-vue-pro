package com.practice.module.system.dal.mysql.usereducation;

import java.util.*;

import com.practice.framework.common.pojo.PageResult;
import com.practice.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.practice.framework.mybatis.core.mapper.BaseMapperX;
import com.practice.module.system.dal.dataobject.usereducation.UserEducationDO;
import org.apache.ibatis.annotations.Mapper;
import com.practice.module.system.controller.admin.usereducation.vo.*;

/**
 * 教育经历 Mapper
 *
 * @author n3
 */
@Mapper
public interface UserEducationMapper extends BaseMapperX<UserEducationDO> {

    default PageResult<UserEducationDO> selectPage(UserEducationPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<UserEducationDO>()
                .eqIfPresent(UserEducationDO::getUserId, reqVO.getUserId())
                .eqIfPresent(UserEducationDO::getMajor, reqVO.getMajor())
                .eqIfPresent(UserEducationDO::getTeacher, reqVO.getTeacher())
                .likeIfPresent(UserEducationDO::getSchoolName, reqVO.getSchoolName())
                .eqIfPresent(UserEducationDO::getDescription, reqVO.getDescription())
                .betweenIfPresent(UserEducationDO::getStartTime, reqVO.getStartTime())
                .betweenIfPresent(UserEducationDO::getEndTime, reqVO.getEndTime())
                .betweenIfPresent(UserEducationDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(UserEducationDO::getId));
    }

    default List<UserEducationDO> selectList(UserEducationExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<UserEducationDO>()
                .eqIfPresent(UserEducationDO::getUserId, reqVO.getUserId())
                .eqIfPresent(UserEducationDO::getMajor, reqVO.getMajor())
                .eqIfPresent(UserEducationDO::getTeacher, reqVO.getTeacher())
                .likeIfPresent(UserEducationDO::getSchoolName, reqVO.getSchoolName())
                .eqIfPresent(UserEducationDO::getDescription, reqVO.getDescription())
                .betweenIfPresent(UserEducationDO::getStartTime, reqVO.getStartTime())
                .betweenIfPresent(UserEducationDO::getEndTime, reqVO.getEndTime())
                .betweenIfPresent(UserEducationDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(UserEducationDO::getId));
    }

}
